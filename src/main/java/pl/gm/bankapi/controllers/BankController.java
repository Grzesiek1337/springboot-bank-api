package pl.gm.bankapi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bank-api")
@AllArgsConstructor
public class BankController {

    @GetMapping
    public String getBankAccountIndexPage() {
        return "bank-account";
    }
}
