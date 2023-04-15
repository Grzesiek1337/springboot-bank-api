package pl.gm.bankapi.client.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class BankClientDto {

    @Column(unique = true)
    @NotBlank(message = "Not blank")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Incorrect email address.")
    private String clientEmail;
    @NotBlank(message = "Not blank")
    @Size(min = 3,max=255, message = "Incorrect password size.")
    private String clientPassword;
    @NotBlank(message = "Not blank")
    @Size(min = 3,max=20, message = "Incorrect first name size.")
    private String clientFirstName;
    @NotBlank(message = "Not blank")
    @Size(min = 3,max=20, message = "Incorrect last name size.")
    private String clientLastName;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate clientDayOfBirth;
}
