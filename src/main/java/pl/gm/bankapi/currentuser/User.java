package pl.gm.bankapi.currentuser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Incorrect email.")
    private String username;
    @NotBlank
    @Size(min = 3,max=255)
    private String password;
    private String role;
    private boolean enabled;
//    @OneToOne(cascade = { CascadeType.REMOVE, CascadeType.MERGE })
//    @JoinColumn(name = "bankaccount_id")
//    private BankAccount bankAccount;

    public User(String username, String password, String role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
