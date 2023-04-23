package pl.gm.bankapi;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BankApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser
    public void shouldAllowAccessToHomePage() throws Exception {
        mockMvc.perform(get("/")
                .cookie(new Cookie("cookie-consent", "true"))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldAllowAccessToAdminPage() throws Exception {
        mockMvc.perform(get("/admin")
                        .cookie(new Cookie("cookie-consent", "true"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/panel"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldDenyAccessToAdminPanelForUser() throws Exception {
        mockMvc.perform(get("/admin")
                        .cookie(new Cookie("cookie-consent", "true"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

}
