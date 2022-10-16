package pl.gm.bankapi.generator;

import java.util.Random;

public class BankAccountNumberGenerator {

    private static final String BANK_ID = "44440000";
    private static final String CHECK_DIGITS = "22";
    private static Random random = new Random();

    public static   String createAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        accountNumber.append(CHECK_DIGITS + BANK_ID);
        for(int i = 0; i <= 15;i++) {
            accountNumber.append(String.valueOf(random.nextInt(0,9)));
        }
        return accountNumber.toString();
    }
}
