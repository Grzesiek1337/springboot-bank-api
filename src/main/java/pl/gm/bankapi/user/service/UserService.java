package pl.gm.bankapi.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.common.PolishBankAccountGenerator;
import pl.gm.bankapi.account.model.BankAccount;
import pl.gm.bankapi.account.repository.BankAccountRepository;
import pl.gm.bankapi.client.dto.BankClientDto;
import pl.gm.bankapi.client.repository.ClientRepository;
import pl.gm.bankapi.communication.email.service.EmailService;
import pl.gm.bankapi.user.dto.UserDto;
import pl.gm.bankapi.user.model.Role;
import pl.gm.bankapi.user.model.User;
import pl.gm.bankapi.client.model.Client;
import pl.gm.bankapi.user.repository.RoleRepository;
import pl.gm.bankapi.user.repository.UserRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    // Autowired repositories and services
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClientRepository clientRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       ClientRepository clientRepository,
                       BankAccountRepository bankAccountRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       ModelMapper modelMapper,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.clientRepository = clientRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    @Transactional
    public void createClient(@Valid BankClientDto bankClient) throws Exception {
        // Create user roles
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.getRoleByName("ROLE_USER"));

        // Create user
        User user = new User();
        user.setUsername(bankClient.getClientEmail());
        user.setPassword(bCryptPasswordEncoder.encode(bankClient.getClientPassword()));
        user.setEmail(bankClient.getClientEmail());
        user.setRoles(userRoles);
        userRepository.save(user);

        // Create client
        Client client = new Client();
        client.setFirstName(bankClient.getClientFirstName());
        client.setLastName(bankClient.getClientLastName());
        client.setDayOfBirth(bankClient.getClientDayOfBirth());
        client.setUser(user);
        clientRepository.save(client);

        // Create bank account
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(PolishBankAccountGenerator.generateAccountNumber());
        bankAccount.setAccountType("Standard");
        bankAccount.setBankName("Money4U");
        bankAccount.setBalance(BigDecimal.valueOf(0.00));
        bankAccount.setClient(client);
        bankAccountRepository.save(bankAccount);

        /** Email sample implementation */
        // Send confirmation email to the new client
//        emailService.sendSimpleMessage(bankClient.getClientEmail(),"Registration account","Success!");
    }

    public void save(UserDto userDto) {
        // Map UserDto to User and save to repository
        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
    }

    public List<UserDto> findAll() {
        // Find all users and map to UserDto
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto findByName(String userName) {
        // Find user by username and map to UserDto
        return modelMapper.map(userRepository.getUserByUsername(userName), UserDto.class);
    }

    public UserDto findByUsernameWithNotifications(String userName) {
        // Find user by username and map to UserDto with notifications
        return modelMapper.map(userRepository.findByUsernameWithNotifications(userName), UserDto.class);
    }

    public void delete(Long id) {
        // Delete user by id
        userRepository.deleteById(id);
    }
}
