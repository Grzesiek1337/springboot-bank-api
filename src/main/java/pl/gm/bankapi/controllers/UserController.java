package pl.gm.bankapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.gm.bankapi.currentuser.CurrentUserDetails;
import pl.gm.bankapi.currentuser.User;
import pl.gm.bankapi.model.BankAccount;
import pl.gm.bankapi.model.BankClient;
import pl.gm.bankapi.model.SavingAccount;
import pl.gm.bankapi.model.StandardAccount;
import pl.gm.bankapi.service.UserService;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/whoami")
    public String whoAmI(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return currentUserDetails.getUser().toString();
    }

    @GetMapping("/create")
    public String createClient(Model model) {
        model.addAttribute("client", new BankClient());
        return "client/create";
    }

    @PostMapping("/create")
    public String saveClientAsUser(@ModelAttribute("client") @Valid BankClient bankClient, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "client/create";
        }
        userService.createUserFromClient(bankClient);
        return "index";
    }
}
