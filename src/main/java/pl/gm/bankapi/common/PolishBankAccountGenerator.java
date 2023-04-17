package pl.gm.bankapi.common;

import java.util.Random;

public class PolishBankAccountGenerator {
    private static final String COUNTRY_CODE = "PL";
    private static final int BANK_CODE_LENGTH = 8;
    private static final int ACCOUNT_NUMBER_LENGTH = 16;
    private static final String[] ACCOUNT_NUMBER_CHARACTERS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static final Random RANDOM = new Random();

    public static String generateAccountNumber() {
        StringBuilder sb = new StringBuilder();
        sb.append(COUNTRY_CODE);
        sb.append(generateControlNumber());
        sb.append(generateBankCode());
        sb.append(generateAccountNumberDigits());
        return sb.toString();
    }

    private static String generateControlNumber() {
        StringBuilder sb = new StringBuilder();
        int controlNumberInt = 98 - generateAccountNumberDigitsMod();
        if (controlNumberInt < 10) {
            sb.append("0");
        }
        sb.append(controlNumberInt);
        return sb.toString();
    }

    private static int generateAccountNumberDigitsMod() {
        String digits = generateAccountNumberDigits();
        int mod = 0;
        for (int i = 0; i < digits.length(); i++) {
            int digit = Integer.parseInt(digits.substring(i, i + 1));
            mod = (mod * 10 + digit) % 97;
        }
        return mod;
    }

    private static String generateBankCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BANK_CODE_LENGTH; i++) {
            sb.append(ACCOUNT_NUMBER_CHARACTERS[RANDOM.nextInt(ACCOUNT_NUMBER_CHARACTERS.length)]);
        }
        return sb.toString();
    }

    private static String generateAccountNumberDigits() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            sb.append(ACCOUNT_NUMBER_CHARACTERS[RANDOM.nextInt(ACCOUNT_NUMBER_CHARACTERS.length)]);
        }
        return sb.toString();
    }
}
