package pl.gm.bankapi.transfer.model;

import lombok.Data;
import pl.gm.bankapi.account.model.BankAccount;
import pl.gm.bankapi.money.Money;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 Represents a transaction history record for a money transfer between two bank accounts.
 */
@Entity
@Table(name = "transaction_history")
@Data
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_id", referencedColumnName = "id")
    private BankAccount fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id", referencedColumnName = "id")
    private BankAccount toAccount;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "date")
    private LocalDateTime date;

    public TransactionHistory() {}

    public TransactionHistory(BankAccount fromAccount, BankAccount toAccount, Money amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount.getAmount();
        this.currency = amount.getCurrency();
        this.date = LocalDateTime.now();
    }
}
