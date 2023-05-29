package pl.gm.bankapi.loan.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.gm.bankapi.account.dto.BankAccountDto;
import pl.gm.bankapi.account.service.BankAccountService;
import pl.gm.bankapi.client.dto.ClientDto;
import pl.gm.bankapi.client.service.ClientService;
import pl.gm.bankapi.communication.email.service.EmailService;
import pl.gm.bankapi.loan.dto.LoanApplicationDto;
import pl.gm.bankapi.loan.dto.LoanDto;
import pl.gm.bankapi.loan.dto.LoanSimulateFormDto;
import pl.gm.bankapi.loan.dto.LoanSimulateResultDto;
import pl.gm.bankapi.loan.interest.InterestRate;
import pl.gm.bankapi.loan.service.LoanService;
import pl.gm.bankapi.communication.notification.service.NotificationService;
import pl.gm.bankapi.payment.dto.PaymentScheduleDto;
import pl.gm.bankapi.payment.service.PaymentService;
import pl.gm.bankapi.user.currentuser.CurrentUserDetails;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    private final ClientService clientService;
    private final PaymentService paymentService;
    private final EmailService emailService;
    private final NotificationService notificationService;
    private final BankAccountService bankAccountService;

    public LoanController(LoanService loanService,
                          ClientService clientService,
                          PaymentService paymentService,
                          EmailService emailService,
                          NotificationService notificationService,
                          BankAccountService bankAccountService) {
        this.loanService = loanService;
        this.clientService = clientService;
        this.paymentService = paymentService;
        this.emailService = emailService;
        this.notificationService = notificationService;
        this.bankAccountService = bankAccountService;
    }

    /**
     * Render the loans view
     */
    @GetMapping
    public String getLoansIndex() {
        return "loan/loans";
    }

    /**
     * Render the simulate loan form
     */
    @GetMapping("/simulate-loan")
    public String simulateLoanForm(Model model) {
        // Set default interest rate and add the form to the model
        LoanSimulateFormDto loanSimulateDto = new LoanSimulateFormDto();
        loanSimulateDto.setInterestRate(InterestRate.ACTUAL.getRate());
        model.addAttribute("loanSimulate", loanSimulateDto);
        return "loan/simulate-loan";
    }

    /**
     * Process the loan simulator form and display the simulated loan result.
     * @param loanSimulateDto The LoanSimulateFormDto object representing the submitted form data.
     * @param bindingResult   The binding result object to check for form validation errors.
     * @param model           The model object to hold the loan simulator results.
     * @return The name of the view to display the simulated loan result.
     */
    @PostMapping("/simulate-loan")
    public String submitSimulateLoanForm(
            @Valid @ModelAttribute("loanSimulate") LoanSimulateFormDto loanSimulateDto,
            BindingResult bindingResult,
            Model model) {

        // Check if there are any validation errors in the submitted form data.
        if (bindingResult.hasErrors()) {
            // If there are validation errors, add an error message to the model and return to the form view.
            model.addAttribute("errorMessage", "Invalid loan simulator data");
            model.addAttribute("loanSimulate", loanSimulateDto);
            return "loan/simulate-loan";
        }

        // Generate the payment schedule for the requested loan amount, interest rate, and payment terms.
        PaymentScheduleDto paymentScheduleDto = paymentService.createPaymentScheduleDto(
                loanSimulateDto.getRequestedLoanAmount(),
                loanSimulateDto.getInterestRate(),
                loanSimulateDto.getPaymentTerms()
        );

        // Simulate the loan using the submitted form data and add the result to the model.
        LoanSimulateResultDto loanSimulateResultDto = loanService.simulateLoan(loanSimulateDto);
        loanSimulateResultDto.setPaymentSchedule(paymentScheduleDto);
        model.addAttribute("simulateResult", loanSimulateResultDto);

        // Return the name of the view to display the simulated loan result.
        return "loan/simulate-result";
    }

    /**
     * Renders the loan application form.
     *
     * @param currentUserDetails the details of the currently authenticated user
     * @param model              the model object for the view
     * @return the view name for the loan application form
     */
    @GetMapping("/application-for-loan")
    public String applicateLoanForm(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        // Get the client information and set default interest rate
        String principalUsername = currentUserDetails.getUsername();
        ClientDto client = clientService.findClientByPrincipalUsername(principalUsername);

        LoanApplicationDto loanApplication = new LoanApplicationDto();
        loanApplication.setUserName(principalUsername);
        loanApplication.setFirstName(client.getFirstName());
        loanApplication.setLastName(client.getLastName());
        loanApplication.setDayOfBirth(client.getDayOfBirth());
        loanApplication.setInterestRate(InterestRate.ACTUAL.getRate());

        // Add the form and client accounts to the model
        model.addAttribute("loanApplication", loanApplication);
        List<BankAccountDto> clientAccounts = bankAccountService.getAccountsByUsername(currentUserDetails.getUsername());
        model.addAttribute("clientAccounts", clientAccounts);

        // Render the loan application form
        return "loan/application-loan-form";
    }

    /**
     * Submit the loan application form and return to the index view
     */
    @PostMapping("/application-for-loan")
    public String submitApplicationLoanForm(
            @Valid @ModelAttribute("loanApplication") LoanApplicationDto loanApplication,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Invalid loan application data");
            model.addAttribute("loanApplication", loanApplication);
            return "loan/application-loan-form";
        }
        loanService.applyForLoan(loanApplication);
        notificationService.addNotificationToUser(loanApplication.getUserName(), "Your loan application has been sent, awaiting confirmation.");
        // emailService.sendSimpleMessage(loanApplication.getUserName(),"Confirmation of the loan application", "Message");
        return "index";
    }

    /**
     * Retrieves all loan applications.
     * @return a list of loan application DTOs
     */
    @GetMapping("/applications")
    @ResponseBody
    public List<LoanApplicationDto> getApplications() {
        return loanService.getAllLoanApplications();
    }

    /**
     * Retrieves the list of loan applications and renders the application list view.
     * @param model the model object for the view
     * @return the view name for the loan application list
     */
    @GetMapping("/applications/list")
    public String getApplicationsList(Model model) {
        model.addAttribute("loanApplications", loanService.getAllLoanApplications());
        return "loan/application-list";
    }

    /**
     * Accepts a loan application with the given application ID and renders the application acceptance view.
     * @param applicationId the ID of the loan application to accept
     * @param model         the model object for the view
     * @return the view name for the loan application acceptance
     */
    @GetMapping("/applications/accept/{applicationId}")
    public String acceptLoanApplication(@PathVariable("applicationId") Long applicationId, Model model) {
        LoanApplicationDto loanApplicationDto = loanService.getLoanApplicationById(applicationId);
        model.addAttribute("loanApplication", loanApplicationDto);
        return "loan/application-accept";
    }

    /**
     * Confirms a loan application with the given application ID and processes the loan approval.
     * @param applicationId the ID of the loan application to confirm
     * @param model         the model object for the view
     * @return the view name for the confirmation page
     */
    @GetMapping("/applications/confirm/{applicationId}")
    @Transactional
    public String confirmLoanApplication(@PathVariable("applicationId") Long applicationId, Model model) {
        // Retrieve the loan application and associated bank account
        LoanApplicationDto loanApplicationDto = loanService.getLoanApplicationById(applicationId);
        BankAccountDto bankAccountDto = bankAccountService.getAccountById(loanApplicationDto.getRequestedAccountId());

        // Generate the payment schedule for the requested loan amount, interest rate, and payment terms
        PaymentScheduleDto paymentScheduleDto = paymentService.createPaymentScheduleDto(
                loanApplicationDto.getRequestedLoanAmount(),
                loanApplicationDto.getInterestRate(),
                loanApplicationDto.getPaymentTerms()
        );

        // Create a new loan based on the loan application and payment schedule
        LoanDto loanDto = new LoanDto();
        loanDto.setOwner(loanApplicationDto.getUserName())
                .setAmount(loanApplicationDto.getRequestedLoanAmount())
                .setRemainsToPaid(loanApplicationDto.getRequestedLoanAmount())
                .setPaymentTerms(loanApplicationDto.getPaymentTerms());

        loanService.createLoan(loanDto, paymentScheduleDto, bankAccountDto);

        // Add a notification to the user
        notificationService.addNotificationToUser(loanApplicationDto.getUserName(), "Your loan application has been accepted!");

        // Send an email notification (if required)
        // emailService.sendSimpleMessage(loanApplication.getUserName(),"Loan", "Message");

        return "index";
    }
}
