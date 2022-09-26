package pl.gm.bankapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;


@AllArgsConstructor
@Getter
@Setter
public abstract class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountType;
    @Column(unique = true)
    private String accountNumber;
    private double accountBalance;

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", accountType='" + accountType + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
