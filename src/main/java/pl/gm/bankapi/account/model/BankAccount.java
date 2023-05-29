package pl.gm.bankapi.account.model;

import lombok.Data;
import pl.gm.bankapi.client.model.Client;
import pl.gm.bankapi.loan.model.LoanEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountType;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<LoanEntity> loans = new ArrayList<>();

}
