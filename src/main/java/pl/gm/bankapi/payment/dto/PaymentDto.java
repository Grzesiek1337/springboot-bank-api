package pl.gm.bankapi.payment.dto;

import lombok.Data;
import pl.gm.bankapi.payment.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A data transfer object representing a payment.
 */
@Data
public class PaymentDto {

    private Long id;
    private int number;
    private BigDecimal amount;
    private LocalDate dueDate;
    private PaymentStatus status;
    private PaymentScheduleDto paymentSchedule;
}
