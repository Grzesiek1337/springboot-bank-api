package pl.gm.bankapi.repositories;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.dto.StandardAccountDto;
import pl.gm.bankapi.generator.BankAccountNumberGenerator;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
public class FakeStandardAccountRepo {
    private final List<StandardAccountDto> accounts;

    public FakeStandardAccountRepo() {
        this.accounts = new ArrayList<>();
        for(int i = 0; i < 10;i++) {
            this.accounts.add(new StandardAccountDto(i + 1L,"Standard",BankAccountNumberGenerator.createAccountNumber(), 12));
        }
    }
}
