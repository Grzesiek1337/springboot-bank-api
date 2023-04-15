package pl.gm.bankapi.client.dto;

import lombok.*;

import java.time.LocalDate;

@Data
public class ClientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;

}
