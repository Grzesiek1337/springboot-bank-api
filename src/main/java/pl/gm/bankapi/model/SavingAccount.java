package pl.gm.bankapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class SavingAccount extends BankAccount{

    private double accountInterest;

    public SavingAccount(Long id, String accountType, String accountNumber, double accountBalance) {
        super(id, accountType, accountNumber, accountBalance);
        this.accountInterest = 5;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", interest=" + accountInterest +
                '}';
    }
}
