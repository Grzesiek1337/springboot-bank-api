package pl.gm.bankapi.transfer.service;

import org.springframework.stereotype.Service;
import pl.gm.bankapi.account.model.BankAccount;
import pl.gm.bankapi.account.repository.BankAccountRepository;
import pl.gm.bankapi.money.Money;
import pl.gm.bankapi.transfer.exception.InsufficientFundsException;
import pl.gm.bankapi.transfer.exception.InvalidAccountNumberException;
import pl.gm.bankapi.transfer.model.TransactionHistory;
import pl.gm.bankapi.transfer.repository.TransactionHistoryRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Currency;

/**

 Service responsible for transferring money between bank accounts.
 */
@Service
public class TransferService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransferService(BankAccountRepository bankAccountRepository, TransactionHistoryRepository transactionHistoryRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    /**
     Transfers the specified amount of money from one bank account to another.
     @param fromAccountNumber the account number to transfer money from
     @param toAccountNumber the account number to transfer money to
     @param amount the amount of money to transfer
     @param currency the currency of the money to transfer
     @throws InsufficientFundsException if the account to transfer from does not have enough funds
     @throws InvalidAccountNumberException if either of the provided account numbers is invalid
     @creates a new TransactionHistory object and saves it in the database
     */

    @Transactional
    public void transferMoney(String fromAccountNumber, String toAccountNumber, Money amount, Currency currency) throws InsufficientFundsException, InvalidAccountNumberException {
        BankAccount fromAccount = bankAccountRepository.findByAccountNumber(fromAccountNumber);
        BankAccount toAccount = bankAccountRepository.findByAccountNumber(toAccountNumber);

        if (fromAccount == null) {
            throw new InvalidAccountNumberException("Invalid account number: " + fromAccountNumber);
        }

        if (toAccount == null) {
            throw new InvalidAccountNumberException("Invalid account number: " + toAccountNumber);
        }

        Money moneyAmount = new Money(amount.getAmount(), currency);

        if (isBalanceLessThan(fromAccount.getBalance(), moneyAmount.getAmount())) {
            throw new InsufficientFundsException("Insufficient funds in account " + fromAccountNumber);
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(moneyAmount.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(moneyAmount.getAmount()));

        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);

        TransactionHistory transaction = new TransactionHistory(fromAccount, toAccount, moneyAmount);
        transactionHistoryRepository.save(transaction);
    }

    /**
     Checks if the account balance is less than the specified amount.
     @param balance the account balance to check
     @param amount the amount to compare the balance to
     @return true if the account balance is less than the specified amount, false otherwise
     */
    private boolean isBalanceLessThan(BigDecimal balance, BigDecimal amount) {
        return balance.compareTo(amount) < 0;
    }
}