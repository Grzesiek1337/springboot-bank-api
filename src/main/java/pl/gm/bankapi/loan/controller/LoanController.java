package pl.gm.bankapi.loan.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.gm.bankapi.client.dto.ClientDto;
import pl.gm.bankapi.client.service.ClientService;
import pl.gm.bankapi.communication.email.service.EmailService;
import pl.gm.bankapi.loan.dto.LoanApplicationDto;
import pl.gm.bankapi.loan.dto.LoanSimulateFormDto;
import pl.gm.bankapi.loan.dto.LoanSimulateResultDto;
import pl.gm.bankapi.loan.interest.InterestRate;
import pl.gm.bankapi.loan.service.LoanService;
import pl.gm.bankapi.payment.dto.PaymentScheduleDto;
import pl.gm.bankapi.payment.service.PaymentService;
import pl.gm.bankapi.user.currentuser.CurrentUserDetails;

import javax.validation.Valid;

@Controller
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    private final ClientService clientService;
    private final PaymentService paymentService;
    private final EmailService emailService;

    public LoanController(LoanService loanService, ClientService clientService, PaymentService paymentService, EmailService emailService) {
        this.loanService = loanService;
        this.clientService = clientService;
        this.paymentService = paymentService;
        this.emailService = emailService;
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
     * @param bindingResult The binding result object to check for form validation errors.
     * @param model The model object to hold the loan simulator results.
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
     * Render the loan application form
     */
    @GetMapping("/applicate-for-loan")
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

        // Add the form to the model
        model.addAttribute("loanApplication", loanApplication);

        // Render the loan application form
        return "loan/application-loan-form";
    }

    /**
     * Submit the loan application form and return to the index view
     */
    @PostMapping("/applicate-for-loan")
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
        // emailService.sendSimpleMessage(loanApplication.getUserName(),"Confirmation of the loan application", "Message");
        return "index";
    }
}
