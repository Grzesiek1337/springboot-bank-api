package pl.gm.bankapi.transfer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gm.bankapi.money.Money;
import pl.gm.bankapi.transfer.exception.InsufficientFundsException;
import pl.gm.bankapi.transfer.exception.InvalidAccountNumberException;
import pl.gm.bankapi.transfer.service.TransferService;

import java.math.BigDecimal;
import java.util.Currency;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    /**
     * Transfers money between two bank accounts.
     * @param fromAccountNumber The account number of the account to transfer money from.
     * @param toAccountNumber   The account number of the account to transfer money to.
     * @param amount            The amount of money to transfer.
     * @return A ResponseEntity containing a success message if the transfer was successful,
     * or a bad request message with an error message if the transfer failed.
     */
    @GetMapping("/{from}/{to}/{amount}")
    public ResponseEntity<String> transferMoney(
            @PathVariable("from") String fromAccountNumber,
            @PathVariable("to") String toAccountNumber,
            @PathVariable("amount") BigDecimal amount
    ) {
        Currency currency = Currency.getInstance("PLN");
        Money money = new Money(amount, currency);
        try {
            transferService.transferMoney(fromAccountNumber, toAccountNumber, money, currency);
            return ResponseEntity.ok("Money transferred successfully");
        } catch (InsufficientFundsException | InvalidAccountNumberException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
