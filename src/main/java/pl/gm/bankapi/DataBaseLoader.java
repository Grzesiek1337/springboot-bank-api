package pl.gm.bankapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.gm.bankapi.account.model.BankAccount;
import pl.gm.bankapi.account.repository.BankAccountRepository;
import pl.gm.bankapi.client.model.Client;
import pl.gm.bankapi.client.repository.ClientRepository;
import pl.gm.bankapi.user.model.Role;
import pl.gm.bankapi.user.model.User;
import pl.gm.bankapi.user.repository.RoleRepository;
import pl.gm.bankapi.user.repository.UserRepository;
import pl.gm.bankapi.user.service.UserService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a Spring Boot class responsible for loading initial data into the database.
 * It implements the CommandLineRunner interface and is annotated with "@Component".
 * The constructor injects several repository objects and a BCryptPasswordEncoder object.
 * The "run" method initializes a set of roles, an admin user, a client, and a bank account,
 * and saves them to the corresponding repositories.
 * This class serves as a database loader and is used for testing purposes.
 * */

@Component
public class DataBaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClientRepository clientRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataBaseLoader(UserRepository userRepository,
                          RoleRepository roleRepository,
                          ClientRepository clientRepository,
                          BankAccountRepository bankAccountRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.clientRepository = clientRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Role adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setId(2L);
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);

        User admin = new User();
        admin.setUsername("aaa");
        admin.setPassword(bCryptPasswordEncoder.encode("aaa"));
        admin.setEmail("admin@example.com");
        admin.setRoles(adminRoles);
        userRepository.save(admin);

        Client client = new Client();
        client.setFirstName("Grzegorz");
        client.setLastName("Miedzianowski");
        client.setDayOfBirth(LocalDate.of(1986,05,15));
        client.setUser(admin);
        clientRepository.save(client);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber("000111222000");
        bankAccount.setAccountType("Standard");
        bankAccount.setBankName("Money4U");
        bankAccount.setBalance(BigDecimal.valueOf(100.00));
        bankAccount.setClient(client);
        bankAccountRepository.save(bankAccount);

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setAccountNumber("000999999999");
        bankAccount2.setAccountType("Saving");
        bankAccount2.setBankName("Money4U");
        bankAccount2.setBalance(BigDecimal.valueOf(0.00));
        bankAccount2.setClient(client);
        bankAccountRepository.save(bankAccount2);
    }
}
