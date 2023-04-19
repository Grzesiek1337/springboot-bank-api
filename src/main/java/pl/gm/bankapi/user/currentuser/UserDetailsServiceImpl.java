package pl.gm.bankapi.user.currentuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.gm.bankapi.user.model.User;
import pl.gm.bankapi.user.repository.UserRepository;

/**
 * Implementation of Spring Security's UserDetailsService interface.
 * Loads a User entity from the database using the UserRepository and maps it to a UserDetails object.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads a User entity from the database using the UserRepository and maps it to a CurrentUserDetails object.
     * Throws a UsernameNotFoundException if the user is not found in the database.
     * @param username the username of the user to load
     * @return a CurrentUserDetails object representing the loaded user
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new CurrentUserDetails(user);
    }
}
