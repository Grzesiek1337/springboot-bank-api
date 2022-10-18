package pl.gm.bankapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "saving_accounts")
public class SavingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double accountInterest;
    private String accountType;
    @Column(unique = true)
    private String accountNumber;
    private double accountBalance;
}
