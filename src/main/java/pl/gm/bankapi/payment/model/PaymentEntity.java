package pl.gm.bankapi.payment.model;

import lombok.Data;
import pl.gm.bankapi.payment.PaymentStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    private BigDecimal amount;

    private LocalDate dueDate;

    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "payment_schedule_id")
    private PaymentScheduleEntity paymentSchedule;
}
