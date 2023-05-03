package pl.gm.bankapi.loan.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loan_applications")
@Data
public class LoanApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;
    private BigDecimal interestRate;
    private BigDecimal requestedLoanAmount;
    private BigDecimal clientPayment;
    private BigDecimal clientExpenses;
    private BigDecimal clientFinancialObligations;
    private int paymentTerms;
    boolean isCreditworthy;
    boolean isCreditApproved;
}
