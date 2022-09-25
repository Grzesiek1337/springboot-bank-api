package pl.gm.bankapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gm.bankapi.currentuser.CurrentUserDetails;
import pl.gm.bankapi.currentuser.User;
import pl.gm.bankapi.model.BankAccount;
import pl.gm.bankapi.model.SavingAccount;
import pl.gm.bankapi.model.StandardAccount;
import pl.gm.bankapi.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user-test-create")
    public User createTestUser() {
        return userService.createTestUser();
    }

    @GetMapping("/whoami")
    public String whoAmI(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return currentUserDetails.getUser().toString();
    }

    @GetMapping("/set-accounts")
    @ResponseBody
    public String setAccounts(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        StandardAccount standardAccount = new StandardAccount(null, "1234", 100.25);
        SavingAccount savingAccount = new SavingAccount(null, "5678", 0.00,7);
        User user = currentUserDetails.getUser();
        List<BankAccount> accounts = user.getBankAccounts();
        accounts.add(standardAccount);
        accounts.add(savingAccount);
        user.setBankAccounts(accounts);
        return accounts.toString();

    }
}
