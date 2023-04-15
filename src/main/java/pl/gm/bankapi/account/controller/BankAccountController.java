package pl.gm.bankapi.account.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.gm.bankapi.account.dto.BankAccountDto;
import pl.gm.bankapi.account.service.BankAccountService;
import pl.gm.bankapi.client.dto.ClientDto;
import pl.gm.bankapi.client.service.ClientService;
import pl.gm.bankapi.user.currentuser.CurrentUserDetails;
import pl.gm.bankapi.user.dto.UserDto;
import pl.gm.bankapi.user.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/account")
public class BankAccountController {

    private final UserService userService;
    private final ClientService clientService;

    private final BankAccountService bankAccountService;

    public BankAccountController(UserService userService, ClientService clientService, BankAccountService bankAccountService) {
        this.userService = userService;
        this.clientService = clientService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public String getAccountIndexPage(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        List<BankAccountDto> accounts = bankAccountService.getAccountsByUsername(currentUserDetails.getUsername());
        model.addAttribute("accounts",accounts);
        return "account/list";
    }
}
