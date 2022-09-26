package pl.gm.bankapi.model;

import lombok.*;

@Getter
@Setter
public class StandardAccount extends BankAccount {
    public StandardAccount(Long id, String accountType, String accountNumber, double accountBalance) {
        super(id, accountType, accountNumber, accountBalance);
    }
}
