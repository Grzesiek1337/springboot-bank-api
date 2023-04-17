package pl.gm.bankapi.money;

import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * Represents a monetary amount with a specific currency.
 */
@Value
public class Money implements Comparable<Money> {

    BigDecimal amount;
    Currency currency;

    /**
     * Constructs a new Money object with the specified amount and currency.
     * The amount is rounded to the appropriate number of decimal places for the currency.
     * @param amount   the amount of money
     * @param currency the currency of the money
     */
    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_UP);
        this.currency = currency;
    }

    /**
     * Adds another Money object to this one and returns the result as a new Money object.
     * Throws an IllegalArgumentException if the currencies do not match.
     * @param other the Money object to add
     * @return a new Money object representing the sum of this and the other Money objects
     */
    public Money add(Money other) {
        checkCurrencyMatch(other);
        return new Money(amount.add(other.amount), currency);
    }

    /**
     * Subtracts another Money object from this one and returns the result as a new Money object.
     * Throws an IllegalArgumentException if the currencies do not match.
     * @param other the Money object to subtract
     * @return a new Money object representing the difference between this and the other Money objects
     */
    public Money subtract(Money other) {
        checkCurrencyMatch(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    /**
     * Returns true if this Money object is less than the other Money object.
     * Throws an IllegalArgumentException if the currencies do not match.
     * @param other the Money object to compare to
     * @return true if this Money object is less than the other Money object
     */
    public boolean isLessThan(Money other) {
        checkCurrencyMatch(other);
        return amount.compareTo(other.amount) < 0;
    }

    /**
     * Returns true if this Money object is greater than or equal to the other Money object.
     * Throws an IllegalArgumentException if the currencies do not match.
     * @param other the Money object to compare to
     * @return true if this Money object is greater than or equal to the other Money object
     */
    public boolean isGreaterThanOrEqual(Money other) {
        checkCurrencyMatch(other);
        return amount.compareTo(other.amount) >= 0;
    }

    /**
     * Throws an IllegalArgumentException if the currencies of this Money object and the other Money object do not match.
     * @param other the Money object to compare the currency to
     */
    private void checkCurrencyMatch(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch: " + currency + " vs " + other.currency);
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return amount.setScale(currency.getDefaultFractionDigits()).toPlainString() + " " + currency.getSymbol();
    }

    /**
     * Compares this Money object to another Money object.
     * Two Money objects can only be compared if they have the same currency.
     * @param other the other Money object to compare to
     * @return an integer representing the comparison result
     * @throws IllegalArgumentException if the two Money objects have different currencies
     */
    @Override
    public int compareTo(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot compare Money objects with different currencies");
        }
        return amount.compareTo(other.amount);
    }
}
