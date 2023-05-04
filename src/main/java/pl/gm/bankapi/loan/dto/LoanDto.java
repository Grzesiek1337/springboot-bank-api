package pl.gm.bankapi.loan.dto;

import lombok.Data;
import pl.gm.bankapi.payment.dto.PaymentScheduleDto;

import java.math.BigDecimal;

@Data public class LoanDto {

    private Long id;
    private String owner;
    private BigDecimal amount;
    private BigDecimal remainsToPaid
    ;private BigDecimal monthlyPayments;
    private int paymentTerms;
    private PaymentScheduleDto paymentScheduleDto;
}
