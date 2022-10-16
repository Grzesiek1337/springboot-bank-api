package pl.gm.bankapi.dto;

import lombok.*;
import pl.gm.bankapi.model.User;


import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;
    private User user;
    private StandardAccountDto standardAccount;
}
