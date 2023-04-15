package pl.gm.bankapi.client.model;

import lombok.Data;
import pl.gm.bankapi.account.model.BankAccount;
import pl.gm.bankapi.user.model.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<BankAccount> bankAccounts = new ArrayList<>();
}
