package pl.gm.bankapi.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gm.bankapi.currentuser.CurrentUserDetails;
import pl.gm.bankapi.dto.StandardAccountDto;

@Controller
@RequestMapping("/bank-api")
@AllArgsConstructor
public class BankController {

    private ModelMapper modelMapper;

    @ModelAttribute("account")
    public StandardAccountDto standardAccount(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return modelMapper.map(currentUserDetails.getUser().getClient().getStandardAccount(),StandardAccountDto.class);
    }

    @GetMapping
    public String getBankAccountIndexPage() {
        return "bank-account";
    }
}
