package pl.gm.bankapi.transfer.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransferDto {

    private String senderAccountNumber;
    @NotBlank(message = "Recipient account number cannot be blank")
    private String recipientAccountNumber;
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than or equal to 0.01")
    private BigDecimal amount;
}
