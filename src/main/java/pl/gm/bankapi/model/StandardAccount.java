package pl.gm.bankapi.model;

import lombok.*;

@Getter
@Setter
public class StandardAccount extends BankAccount {
    public StandardAccount(Long id, String accountNumber, double accountBalance) {
        super(id, accountNumber, accountBalance);
    }
}
