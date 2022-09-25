package pl.gm.bankapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter

public class SavingAccount extends BankAccount{

    private double interest;

    public SavingAccount(Long id, String accountNumber, double accountBalance, double interest) {
        super(id, accountNumber, accountBalance);
        this.interest = interest;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", interest=" + interest +
                '}';
    }
}
