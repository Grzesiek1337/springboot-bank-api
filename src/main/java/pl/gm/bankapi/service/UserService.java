package pl.gm.bankapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.currentuser.User;
import pl.gm.bankapi.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createTestUser() {
        User user = new User();
        user.setUsername("gm116@gmail.com");
        user.setPassword(bCryptPasswordEncoder.encode("aaa"));
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        userRepository.save(user);
        return user;
    }
}
