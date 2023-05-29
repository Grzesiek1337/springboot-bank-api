package pl.gm.bankapi.loan.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * A data transfer object representing a loan.
 */
@Data
@Accessors(chain = true)
public class LoanDto {

    private Long id;
    private String owner;
    private BigDecimal amount;
    private BigDecimal remainsToPaid;
    private int paymentTerms;
}
