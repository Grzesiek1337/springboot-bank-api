package pl.gm.bankapi.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.model.User;
import pl.gm.bankapi.dto.BankClientDto;
import pl.gm.bankapi.dto.ClientDto;
import pl.gm.bankapi.dto.StandardAccountDto;
import pl.gm.bankapi.generator.BankAccountNumberGenerator;
import pl.gm.bankapi.model.Client;
import pl.gm.bankapi.repositories.ClientRepository;
import pl.gm.bankapi.repositories.UserRepository;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private ClientService clientService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ModelMapper modelMapper;

    @Transactional
    public void createClient(BankClientDto bankClient) throws Exception {
        StandardAccountDto standardAccount = new StandardAccountDto();
        standardAccount.setAccountType("Standard");
        standardAccount.setAccountBalance(0.00);
        standardAccount.setAccountNumber(BankAccountNumberGenerator.createAccountNumber());

        User user = new User();
        user.setUsername(bankClient.getClientEmail());
        user.setPassword(bCryptPasswordEncoder.encode(bankClient.getClientPassword()));
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        userRepository.save(modelMapper.map(user,User.class));

        ClientDto client = new ClientDto();
        client.setFirstName(bankClient.getClientFirstName());
        client.setLastName(bankClient.getClientLastName());
        client.setDayOfBirth(bankClient.getClientDayOfBirth());
        client.setUser(user);
        client.setStandardAccount(standardAccount);
        standardAccount.setClient(client);
        clientService.save(modelMapper.map(client, Client.class));
    }
}
