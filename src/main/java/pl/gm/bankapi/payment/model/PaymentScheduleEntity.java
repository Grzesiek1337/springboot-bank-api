package pl.gm.bankapi.payment.model;

import lombok.Data;
import pl.gm.bankapi.loan.model.LoanEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "payment_schedules")
@Data
public class PaymentScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "loan_id")
    private LoanEntity loan;

    @OneToMany(mappedBy = "paymentSchedule", cascade = CascadeType.ALL)
    private List<PaymentEntity> payments;
}
