package pl.gm.bankapi.dto;

import pl.gm.bankapi.model.BankAccount;

import java.util.List;

public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String role;
    private boolean enabled;
    private List<BankAccount> bankAccounts;
}
