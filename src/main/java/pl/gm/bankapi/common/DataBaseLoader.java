package pl.gm.bankapi.common;

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

    /**
     This method initializes the database with sample data for testing and development purposes.
     It creates users with roles, clients, and bank accounts, and saves them to the respective repositories.
     */
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

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

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
        bankAccount.setAccountNumber("1");
        bankAccount.setAccountType("Standard");
        bankAccount.setBankName("Money4U");
        bankAccount.setBalance(BigDecimal.valueOf(100.00));
        bankAccount.setClient(client);
        bankAccountRepository.save(bankAccount);

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setAccountNumber("2");
        bankAccount2.setAccountType("Saving");
        bankAccount2.setBankName("Money4U");
        bankAccount2.setBalance(BigDecimal.valueOf(0.00));
        bankAccount2.setClient(client);
        bankAccountRepository.save(bankAccount2);

        User user = new User();
        user.setUsername("bbb");
        user.setPassword(bCryptPasswordEncoder.encode("bbb"));
        user.setEmail("user@example.com");
        user.setRoles(userRoles);
        userRepository.save(user);

        Client client2 = new Client();
        client2.setFirstName("Jan");
        client2.setLastName("Zapałka");
        client2.setDayOfBirth(LocalDate.of(1976,11,11));
        client2.setUser(user);
        clientRepository.save(client2);

        BankAccount bankAccount3 = new BankAccount();
        bankAccount3.setAccountNumber("3");
        bankAccount3.setAccountType("Standard");
        bankAccount3.setBankName("Money4U");
        bankAccount3.setBalance(BigDecimal.valueOf(0.00));
        bankAccount3.setClient(client2);
        bankAccountRepository.save(bankAccount3);

        User user2 = new User();
        user2.setUsername("ccc");
        user2.setPassword(bCryptPasswordEncoder.encode("ccc"));
        user2.setEmail("user2@example.com");
        user2.setRoles(userRoles);
        userRepository.save(user2);

        Client client3 = new Client();
        client3.setFirstName("Jan");
        client3.setLastName("Zapałka");
        client3.setDayOfBirth(LocalDate.of(1948,01,24));
        client3.setUser(user2);
        clientRepository.save(client3);

        BankAccount bankAccount4 = new BankAccount();
        bankAccount4.setAccountNumber("4");
        bankAccount4.setAccountType("Standard");
        bankAccount4.setBankName("Money4U");
        bankAccount4.setBalance(BigDecimal.valueOf(15.00));
        bankAccount4.setClient(client3);
        bankAccountRepository.save(bankAccount4);
    }
}
