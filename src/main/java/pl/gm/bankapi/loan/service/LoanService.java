package pl.gm.bankapi.loan.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.loan.dto.LoanApplicationDto;
import pl.gm.bankapi.loan.dto.LoanSimulateFormDto;
import pl.gm.bankapi.loan.dto.LoanSimulateResultDto;
import pl.gm.bankapi.loan.model.LoanApplicationEntity;
import pl.gm.bankapi.loan.repository.LoanApplicationRepository;
import pl.gm.bankapi.loan.repository.LoanRepository;
import pl.gm.bankapi.money.Money;
import pl.gm.bankapi.payment.PaymentCalculator;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    private final ModelMapper modelMapper;

    public LoanService(LoanRepository loanRepository, LoanApplicationRepository loanApplicationRepository, ModelMapper modelMapper) {
        this.loanRepository = loanRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Simulates a loan based on the given input parameters.
     * @param loan the input parameters for the loan simulation.
     * @return a DTO containing the simulated loan details.
     */
    public LoanSimulateResultDto simulateLoan(LoanSimulateFormDto loan) {

        // Constants used in the loan simulation
        final Money INTEREST_RATE_DIVIDER = new Money(BigDecimal.valueOf(100.00));

        // Calculate loan details
        Money requestedLoanAmount = new Money(loan.getRequestedLoanAmount());
        Money interestRatePercentage = new Money(loan.getInterestRate())
                .divide(INTEREST_RATE_DIVIDER, 2, RoundingMode.HALF_UP);
        Money totalLoanCost = requestedLoanAmount.add(requestedLoanAmount.multiply(interestRatePercentage));
        Money monthlyPayment = PaymentCalculator.calculateMonthlyPayment(requestedLoanAmount,
                interestRatePercentage, loan.getPaymentTerms());
        Money amountOfInterest = requestedLoanAmount.multiply(interestRatePercentage);

        // Create and populate the DTO with the loan details
        LoanSimulateResultDto loanSimulateResult = new LoanSimulateResultDto();
        loanSimulateResult.setAmount(requestedLoanAmount);
        loanSimulateResult.setInterestRate(loan.getInterestRate());
        loanSimulateResult.setTotalLoanCost(totalLoanCost);
        loanSimulateResult.setAmountOfInterest(amountOfInterest);
        loanSimulateResult.setMonthlyPayment(monthlyPayment);
        loanSimulateResult.setPaymentTerms(loan.getPaymentTerms());

        return loanSimulateResult;
    }

    /**
     * Processes a loan application by checking the applicant's creditworthiness, and if approved, saving the application to the database.
     * @param loanApplication the loan application to process.
     */
    public void applyForLoan(LoanApplicationDto loanApplication) {

        // Calculate monthly payment based on requested loan amount and interest rate
        Money requestedLoanAmount = new Money(loanApplication.getRequestedLoanAmount());
        Money interestRate = new Money(loanApplication.getInterestRate());
        Money monthlyPayment = PaymentCalculator.calculateMonthlyPayment(requestedLoanAmount,
                interestRate, loanApplication.getPaymentTerms());

        // Check creditworthiness of the applicant
        boolean isCreditworthy = isCreditworthy(loanApplication.getClientPayment(),
                loanApplication.getClientExpenses(),
                loanApplication.getClientFinancialObligations(),
                monthlyPayment.getAmount());

        // Set creditworthiness and credit approval status on the loan application
        loanApplication.setCreditworthy(isCreditworthy);
        loanApplication.setCreditApproved(false);

        // Convert the DTO to an entity and save to the database
        LoanApplicationEntity loan = modelMapper.map(loanApplication, LoanApplicationEntity.class);
        loanApplicationRepository.save(loan);

    }

    /**
     * Determines whether a client is creditworthy based on their financial information and the requested loan details.
     * @param clientSalary         The client's monthly salary.
     * @param clientExpenses       The client's monthly expenses.
     * @param financialObligations The client's monthly financial obligations (e.g. credit card payments, other loans).
     * @param creditInstallment    The monthly installment amount for the requested loan.
     * @return true if the client is creditworthy, false otherwise.
     */
    private boolean isCreditworthy(BigDecimal clientSalary, BigDecimal clientExpenses, BigDecimal financialObligations, BigDecimal creditInstallment) {
        // Calculate the client's net income and maximum monthly installment based on their salary and expenses.
        BigDecimal netIncome = clientSalary.subtract(clientExpenses);
        BigDecimal maxInstallment = netIncome.multiply(new BigDecimal("0.4"));

        // If the requested monthly installment is greater than the maximum allowed, the client is not creditworthy.
        if (creditInstallment.compareTo(maxInstallment) > 0) {
            return false;
        }

        // Calculate the client's available monthly income based on their net income and financial obligations.
        BigDecimal freeIncome = netIncome.multiply(new BigDecimal("0.6"));
        BigDecimal availableAmount = freeIncome.multiply(new BigDecimal("0.5")).subtract(financialObligations);

        // If the client has negative available income after deducting their financial obligations, they are not creditworthy.
        if (availableAmount.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        // Calculate the remaining number of monthly installments the client can afford based on their available income and the requested monthly installment.
        BigDecimal remainingInstallments = availableAmount.divide(creditInstallment, 0, RoundingMode.UP);

        // If the remaining number of installments is greater than 12, the client is not creditworthy.
        if (remainingInstallments.compareTo(new BigDecimal("12")) > 0) {
            return false;
        }

        // If none of the above conditions are met, the client is creditworthy.
        return true;
    }
}
