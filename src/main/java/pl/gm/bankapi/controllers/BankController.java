package pl.gm.bankapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bank-api")
public class BankController {

    @GetMapping
    public String getBankAccountIndex() {
        return "bank-account";
    }
}
