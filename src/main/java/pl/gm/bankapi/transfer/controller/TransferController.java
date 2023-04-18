package pl.gm.bankapi.transfer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.gm.bankapi.account.service.BankAccountService;
import pl.gm.bankapi.money.Money;
import pl.gm.bankapi.transfer.dto.TransferDto;
import pl.gm.bankapi.transfer.exception.InsufficientFundsException;
import pl.gm.bankapi.transfer.exception.InvalidAccountNumberException;
import pl.gm.bankapi.transfer.service.TransferService;
import pl.gm.bankapi.user.currentuser.CurrentUserDetails;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Currency;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;
    private final BankAccountService bankAccountService;

    public TransferController(TransferService transferService, BankAccountService bankAccountService) {
        this.transferService = transferService;
        this.bankAccountService = bankAccountService;
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
    @ResponseBody
    public ResponseEntity<String> transferMoney(
            @PathVariable("from") String fromAccountNumber,
            @PathVariable("to") String toAccountNumber,
            @PathVariable("amount") BigDecimal amount
    ) {
        Currency currency = Currency.getInstance("PLN");
        Money money = new Money(amount, currency);
        try {
            transferService.transferMoney(fromAccountNumber, toAccountNumber, money);
            return ResponseEntity.ok("Money transferred successfully");
        } catch (InsufficientFundsException | InvalidAccountNumberException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/new/{senderAccountNumber}")
    public String showTransferForm(
            @PathVariable String senderAccountNumber,
            @AuthenticationPrincipal CurrentUserDetails currentUserDetails,
            Model model) {
        TransferDto transferDto = new TransferDto();
        transferDto.setSenderAccountNumber(senderAccountNumber);
        model.addAttribute("transferDto", transferDto);
        return "transfer/new";
    }

    @PostMapping("/new")
    public String transferMoney(
            @Valid @ModelAttribute TransferDto transferDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Invalid transfer data");
            model.addAttribute("transferDto", transferDto);
            return "transfer/new";
        }

        Money money = new Money(transferDto.getAmount());
        try {
            transferService.transferMoney(transferDto.getSenderAccountNumber(), transferDto.getRecipientAccountNumber(), money);
            return "redirect:/account";
        } catch (InsufficientFundsException | InvalidAccountNumberException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("transferDto", transferDto);
            return "transfer/new";
        }
    }
}
