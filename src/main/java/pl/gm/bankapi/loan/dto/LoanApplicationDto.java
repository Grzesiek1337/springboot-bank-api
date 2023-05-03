package pl.gm.bankapi.loan.dto;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The LoanApplicationDto class represents a DTO object that holds loan application data entered by the user.
 * It includes personal information about the applicant, requested loan amount, loan terms, and financial data.
 * Additionally, it contains two flags indicating whether the applicant is creditworthy and whether their loan application was approved.
 */
@Data
public class LoanApplicationDto {

    private String userName;
    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;
    private BigDecimal interestRate;
    @Digits(integer=10, fraction=2)
    @Min(value=500, message="Requested loan amount must be at least 500.")
    private BigDecimal requestedLoanAmount;
    @Digits(integer=10, fraction=2)
    private BigDecimal clientPayment;
    @Digits(integer=10, fraction=2)
    private BigDecimal clientExpenses;
    @Digits(integer=10, fraction=2)
    private BigDecimal clientFinancialObligations;
    @Min(3)
    @Max(72)
    @Positive
    private int paymentTerms;
    boolean isCreditworthy;
    boolean isCreditApproved;
}
