package pl.gm.bankapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Component
@Getter
@Setter
public class BankClient {
    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Incorrect email address.")
    private String clientEmail;
    @NotBlank
    @Size(min = 3,max=255, message = "Incorrect password size.")
    private String clientPassword;
}
