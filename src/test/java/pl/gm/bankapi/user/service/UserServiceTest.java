package pl.gm.bankapi.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.gm.bankapi.user.dto.UserDto;
import pl.gm.bankapi.user.model.Role;
import pl.gm.bankapi.user.model.User;
import pl.gm.bankapi.user.repository.RoleRepository;
import pl.gm.bankapi.user.repository.UserRepository;


import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSaveUser() {
        // given
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setPassword("password");

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.getRoleByName("ROLE_USER"));
        user.setRoles(userRoles);

        // when
        when(modelMapper.map(userDto, User.class)).thenReturn(user);
        userService.save(userDto);

        // then
        verify(userRepository).save(user);
        assertNotNull(user.getRoles());
    }
}