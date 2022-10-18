package pl.gm.bankapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SavingAccountDto extends BankAccount {

    private double accountInterest;

    public SavingAccountDto(Long id, String accountType, String accountNumber, double accountBalance) {
        super(id, accountType, accountNumber, accountBalance);
        this.accountInterest = 5;
    }
}
