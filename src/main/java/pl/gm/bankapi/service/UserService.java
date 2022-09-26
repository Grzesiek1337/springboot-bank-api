package pl.gm.bankapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.currentuser.User;
import pl.gm.bankapi.model.BankClient;
import pl.gm.bankapi.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<String> getAllUserNames() {
        return userRepository.findAll().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    public void createUserFromClient(BankClient bankClient) {
        User user = new User();
        user.setUsername(bankClient.getClientEmail());
        user.setPassword(bCryptPasswordEncoder.encode(bankClient.getClientPassword()));
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        userRepository.save(user);


    }


}
