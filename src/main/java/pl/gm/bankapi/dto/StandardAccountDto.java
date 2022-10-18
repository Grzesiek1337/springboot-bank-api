package pl.gm.bankapi.dto;

import lombok.*;
import org.springframework.stereotype.Component;


@Getter
@Setter
@NoArgsConstructor
@Component
public class StandardAccountDto extends BankAccount {

    private ClientDto client;
    public StandardAccountDto(Long id, String accountType, String accountNumber, double accountBalance) {
        super(id, accountType, accountNumber, accountBalance);
    }
}
