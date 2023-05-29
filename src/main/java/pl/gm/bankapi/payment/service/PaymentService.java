package pl.gm.bankapi.payment.service;

import org.springframework.stereotype.Service;
import pl.gm.bankapi.money.Money;
import pl.gm.bankapi.payment.PaymentCalculator;
import pl.gm.bankapi.payment.PaymentStatus;
import pl.gm.bankapi.payment.dto.PaymentDto;
import pl.gm.bankapi.payment.dto.PaymentScheduleDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A service class for creating payment schedules.
 */
@Service
public class PaymentService {

    /**
     * Creates a payment schedule based on the given loan amount, interest rate, and payment terms.
     * @param amount        the loan amount
     * @param interestRate  the interest rate
     * @param paymentTerms  the number of payment terms
     * @return              a PaymentScheduleDto object representing the payment schedule
     */
    public PaymentScheduleDto createPaymentScheduleDto(BigDecimal amount, BigDecimal interestRate, int paymentTerms) {

        // Create Money objects for the loan amount, loan interest rate, and monthly payment
        Money loanAmount = new Money(amount);
        Money loanInterest = new Money(interestRate);
        Money monthlyPayment = PaymentCalculator.calculateMonthlyPayment(loanAmount, loanInterest, paymentTerms);

        // Create a new PaymentScheduleDto object
        PaymentScheduleDto paymentSchedule = new PaymentScheduleDto();

        // Create a list of PaymentDto objects representing each payment
        List<PaymentDto> paymentDtos = new ArrayList<>();
        LocalDate paymentDate = LocalDate.now().plusMonths(1);

        // Create a PaymentDto object for each payment term and add it to the list
        for (int i = 1; i <= paymentTerms; i++) {
            PaymentDto payment = new PaymentDto();
            payment.setNumber(i);
            payment.setAmount(monthlyPayment.getAmount());
            payment.setDueDate(paymentDate);
            payment.setStatus(PaymentStatus.PENDING);
            paymentDtos.add(payment);
            paymentDate = paymentDate.plusMonths(1);
        }

        // Set the list of payments on the payment schedule and return it
        paymentSchedule.setPayments(paymentDtos);
        return paymentSchedule;
    }
}
