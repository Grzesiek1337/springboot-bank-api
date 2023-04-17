package pl.gm.bankapi.transfer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {

    private String senderAccountNumber;
    private String recipientAccountNumber;
    private BigDecimal amount;
}
