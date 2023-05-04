package pl.gm.bankapi.payment;

import pl.gm.bankapi.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaymentCalculator {

    /**
     * Calculates the monthly payment for a loan with the given parameters.
     * @param loanAmount the amount of the loan
     * @param loanInterestRate the interest rate of the loan
     * @param loanTermInMonths the term of the loan in months
     * @return the monthly payment for the loan
     */
    public static Money calculateMonthlyPayment(Money loanAmount, Money loanInterestRate, int loanTermInMonths) {

        Money loanInterestDivider = loanInterestRate.divide(new Money(BigDecimal.valueOf(100.00)), 2, RoundingMode.HALF_UP);
        Money loanAmountWithInterest = loanAmount.add(loanAmount.multiply(loanInterestDivider));
        Money terms = new Money(BigDecimal.valueOf(loanTermInMonths));
        return loanAmountWithInterest.divide(terms, 2, RoundingMode.HALF_UP);
    }
}
