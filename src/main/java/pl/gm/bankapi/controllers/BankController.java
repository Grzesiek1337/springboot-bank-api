package pl.gm.bankapi.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gm.bankapi.currentuser.CurrentUserDetails;
import pl.gm.bankapi.model.BankAccount;

import java.util.List;

@Controller
@RequestMapping("/bank-api")
public class BankController {

    @ModelAttribute("accounts")
    public List<BankAccount> userBankAccounts(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return currentUserDetails.getUser().getBankAccounts();
    }

    @GetMapping
    public String getBankAccountIndex() {
        return "bank-account";
    }
}
