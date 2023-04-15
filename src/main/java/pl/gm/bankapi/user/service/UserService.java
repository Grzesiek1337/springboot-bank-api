package pl.gm.bankapi.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.account.model.BankAccount;
import pl.gm.bankapi.account.repository.BankAccountRepository;
import pl.gm.bankapi.client.dto.BankClientDto;
import pl.gm.bankapi.client.repository.ClientRepository;
import pl.gm.bankapi.user.dto.UserDto;
import pl.gm.bankapi.user.model.Role;
import pl.gm.bankapi.user.model.User;
import pl.gm.bankapi.client.model.Client;
import pl.gm.bankapi.user.repository.RoleRepository;
import pl.gm.bankapi.user.repository.UserRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClientRepository clientRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ClientRepository clientRepository, BankAccountRepository bankAccountRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.clientRepository = clientRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void createClient(@Valid BankClientDto bankClient) throws Exception {

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.getRoleByName("ROLE_USER"));


        User user = new User();
        user.setUsername(bankClient.getClientEmail());
        user.setPassword(bCryptPasswordEncoder.encode(bankClient.getClientPassword()));
        user.setEmail(bankClient.getClientEmail());
        user.setRoles(userRoles);
        userRepository.save(user);

        Client client = new Client();
        client.setFirstName(bankClient.getClientFirstName());
        client.setLastName(bankClient.getClientLastName());
        client.setDayOfBirth(bankClient.getClientDayOfBirth());
        client.setUser(user);
        clientRepository.save(client);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber("000111222000");
        bankAccount.setBankName("Money4U");
        bankAccount.setBalance(BigDecimal.valueOf(100.00));
        bankAccount.setClient(client);
        bankAccountRepository.save(bankAccount);
    }

    public void save(User user) {
        userRepository.save(user);
    }


    public Iterable<UserDto> getAll() {
        Iterable<User> users = userRepository.findAll();
        return modelMapper.map(users,Iterable.class);
    }
    public UserDto findByName(String userName) {
        UserDto userDto = modelMapper.map(userRepository.getUserByUsername(userName),UserDto.class);
        return userDto;
    }
}
