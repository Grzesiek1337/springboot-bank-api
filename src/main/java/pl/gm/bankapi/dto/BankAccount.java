package pl.gm.bankapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class BankAccount {

    private Long id;
    private String accountType;
    private String accountNumber;
    private double accountBalance;
}
