package pl.gm.bankapi.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pl.gm.bankapi.client.model.Client;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(cascade = { CascadeType.REMOVE, CascadeType.MERGE })
    @JoinColumn(name = "client_id")
    private Client client;

}
