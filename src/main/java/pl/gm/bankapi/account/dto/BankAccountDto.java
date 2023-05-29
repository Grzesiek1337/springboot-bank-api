package pl.gm.bankapi.account.dto;

import lombok.Data;
import pl.gm.bankapi.client.dto.ClientDto;

import java.math.BigDecimal;

@Data
public class BankAccountDto {

    private Long id;
    private String accountNumber;
    private String accountType;
    private String bankName;
    private BigDecimal balance;
    private ClientDto client;
}
