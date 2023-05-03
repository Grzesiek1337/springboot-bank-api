package pl.gm.bankapi.loan.interest;

import java.math.BigDecimal;

/**
 * The InterestRate enum represents different interest rates that can be used in the loan simulation.
 * Each interest rate has a specific value expressed as a BigDecimal percentage.
 */
public enum InterestRate {

    LOW(new BigDecimal("5.00")),
    MEDIUM(new BigDecimal("8.00")),
    HIGH(new BigDecimal("12.00")),
    ACTUAL(new BigDecimal("9.00"));

    private BigDecimal rate;

    InterestRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
