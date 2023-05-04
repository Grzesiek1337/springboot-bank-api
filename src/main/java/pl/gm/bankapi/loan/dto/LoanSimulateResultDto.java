package pl.gm.bankapi.loan.dto;

import lombok.Data;
import pl.gm.bankapi.money.Money;
import pl.gm.bankapi.payment.dto.PaymentScheduleDto;

import java.math.BigDecimal;

/**
 * Class for loan simulation result.
 */
@Data
public class LoanSimulateResultDto {

    private Money amount;
    private BigDecimal interestRate;
    private Money totalLoanCost;
    private Money amountOfInterest;
    private Money monthlyPayment;
    private int paymentTerms;
    private PaymentScheduleDto paymentSchedule;
}
