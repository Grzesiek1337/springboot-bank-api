package pl.gm.bankapi.generator;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BankAccountNumberGenerator {
    private final String BANK_ID = "44440000";
    private final String CHECK_DIGITS = "22";

    private Random random = new Random();

    public  String createAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        accountNumber.append(CHECK_DIGITS + BANK_ID);
        for(int i = 0; i <= 15;i++) {
            accountNumber.append(String.valueOf(random.nextInt(0,9)));
        }
        return accountNumber.toString();
    }
}
