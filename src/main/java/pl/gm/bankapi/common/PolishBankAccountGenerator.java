package pl.gm.bankapi.common;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * This class generates valid Polish bank account numbers according to the standard format.
 * The account number consists of the two-letter country code ("PL"), a two-digit control number,
 * an eight-digit bank code, and a sixteen-digit account number.
 */
@Component
public class PolishBankAccountGenerator {

    /** The two-letter country code for Poland */
    private static final String COUNTRY_CODE = "PL";

    /** The length of the bank code */
    private static final int BANK_CODE_LENGTH = 8;

    /** The length of the account number */
    private static final int ACCOUNT_NUMBER_LENGTH = 16;

    /** The possible characters in the account number */
    private static final String[] ACCOUNT_NUMBER_CHARACTERS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    /** The random number generator used to generate account numbers */
    private static final Random RANDOM = new Random();

    /**
     * Generates a complete Polish bank account number.
     * @return the bank account number as a String
     */
    public static String generateAccountNumber() {
        StringBuilder sb = new StringBuilder();
        sb.append(COUNTRY_CODE); // add country code
        sb.append(generateControlNumber()); // add control number
        sb.append(generateBankCode()); // add bank code
        sb.append(generateAccountNumberDigits()); // add account number digits
        return sb.toString();
    }

    /**
     * Generates the two-digit control number for the bank account.
     * @return the control number as a String
     */
    private static String generateControlNumber() {
        StringBuilder sb = new StringBuilder();
        int controlNumberInt = 98 - generateAccountNumberDigitsMod();
        if (controlNumberInt < 10) {
            sb.append("0");
        }
        sb.append(controlNumberInt);
        return sb.toString();
    }

    /**
     * Calculates the modulo 97 of the account number digits, which is used to generate the control number.
     * @return the modulo 97 as an int
     */
    private static int generateAccountNumberDigitsMod() {
        String digits = generateAccountNumberDigits();
        int mod = 0;
        for (int i = 0; i < digits.length(); i++) {
            int digit = Integer.parseInt(digits.substring(i, i + 1));
            mod = (mod * 10 + digit) % 97;
        }
        return mod;
    }

    /**
     * Generates a random bank code of the specified length using digits 0-9.
     * @return the bank code as a String
     */
    private static String generateBankCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BANK_CODE_LENGTH; i++) {
            sb.append(ACCOUNT_NUMBER_CHARACTERS[RANDOM.nextInt(ACCOUNT_NUMBER_CHARACTERS.length)]);
        }
        return sb.toString();
    }

    /**
     * Generates a random account number of the specified length using digits 0-9.
     * @return the account number as a String
     */
    private static String generateAccountNumberDigits() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            sb.append(ACCOUNT_NUMBER_CHARACTERS[RANDOM.nextInt(ACCOUNT_NUMBER_CHARACTERS.length)]);
        }
        return sb.toString();
    }
}
