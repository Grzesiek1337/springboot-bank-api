package pl.gm.bankapi.loan.model;

import lombok.Data;
import pl.gm.bankapi.account.model.BankAccount;
import pl.gm.bankapi.payment.model.PaymentScheduleEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "loans")
@Data
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String owner;
    private BigDecimal amount;
    private BigDecimal remainsToPaid;
    private int paymentTerms;

    @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL)
    private PaymentScheduleEntity paymentSchedule;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;
}
