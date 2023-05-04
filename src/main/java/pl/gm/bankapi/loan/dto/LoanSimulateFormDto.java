package pl.gm.bankapi.loan.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Class for loan simulation and inquiry form.
 */
@Data
public class LoanSimulateFormDto {

    @Digits(integer=10, fraction=2)
    private BigDecimal requestedLoanAmount;
    private BigDecimal interestRate;
    @Min(3)
    @Max(72)
    @Positive
    private int paymentTerms;
}
