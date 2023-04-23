package pl.gm.bankapi.user.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import pl.gm.bankapi.client.dto.BankClientDto;
import pl.gm.bankapi.user.dto.RoleDto;
import pl.gm.bankapi.user.dto.UserDto;

import pl.gm.bankapi.user.repository.UserRepository;
import pl.gm.bankapi.user.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private String csrfToken;

    private String accessToken;

    @BeforeEach
    public void setUp() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("aaa");
        user.setPassword("testpassword");
        Set<RoleDto> adminRoles = new HashSet<>();
        RoleDto adminRole = new RoleDto();
        adminRole.setName("ADMIN");
        adminRole.setId(1L);
        user.setRoles(adminRoles);
        userService.save(user);

        MockHttpServletRequestBuilder loginBuilder = post("/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", user.getUsername())
                .param("password", user.getPassword());

        MvcResult loginResult = mockMvc.perform(loginBuilder)
                .andExpect(status().is3xxRedirection())
                .andReturn();

        String location = loginResult.getResponse().getHeader("Location");
        String[] segments = location.split("\\?");

        String authorizationEndpoint = segments[0];
        String csrfTokenName = "_csrf";
        String csrfParameterValue = loginResult.getRequest().getAttribute(csrfTokenName).toString();

        MultiValueMap<String, String> authorizationParameters = new LinkedMultiValueMap<>();
        authorizationParameters.add(csrfTokenName, csrfParameterValue);

        ResultActions authorize = mockMvc.perform(get(authorizationEndpoint).params(authorizationParameters))
                .andExpect(status().isOk());

        MvcResult result = authorize.andReturn();

        CsrfToken token = (CsrfToken) result.getRequest().getAttribute(CsrfToken.class.getName());
        this.csrfToken = token.getToken();

        String authorizationHeader = result.getResponse().getHeader("Authorization");
        this.accessToken = authorizationHeader.split(" ")[1];
    }

    @Test
    void testCreateClient() throws Exception {
        BankClientDto client = new BankClientDto();
        client.setClientEmail("test@example.com");
        client.setClientPassword("password");
        client.setClientFirstName("John");
        client.setClientLastName("Doe");
        client.setClientDayOfBirth(LocalDate.of(1990, 1, 1));

        mockMvc.perform(post("/client/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(userService, times(1)).createClient(client);
    }

    @Test
    void testCreateClientWithInvalidData() throws Exception {
        BankClientDto client = new BankClientDto();
        client.setClientEmail("invalid-email");
        client.setClientPassword("pass");
        client.setClientFirstName("");
        client.setClientLastName("");
        client.setClientDayOfBirth(LocalDate.of(2020, 1, 1));

        mockMvc.perform(post("/client/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(view().name("client/create"))
                .andExpect(model().attributeHasFieldErrors("client", "clientEmail", "clientPassword",
                        "clientFirstName", "clientLastName", "clientDayOfBirth"));

        verify(userService, never()).createClient(client);
    }

    @Test
    void testGetAllUsers() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("aaa");

        when(userService.findByName("aaa")).thenReturn(user);

        mockMvc.perform(get("index")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));

        verify(userService, times(1)).findByName("aaa");
    }
}