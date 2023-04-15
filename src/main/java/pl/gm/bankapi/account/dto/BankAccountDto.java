package pl.gm.bankapi.account.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountDto {

    private Long id;
    private String accountNumber;
    private String accountType;
    private String bankName;
    private BigDecimal balance;

}
