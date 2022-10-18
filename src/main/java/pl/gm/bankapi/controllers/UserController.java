package pl.gm.bankapi.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import pl.gm.bankapi.dto.BankClientDto;
import pl.gm.bankapi.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    @GetMapping("/create")
    public String createClient(Model model) {
        model.addAttribute("client", new BankClientDto());
        return "client/create";
    }

    @PostMapping("/create")
    public String saveClient(@ModelAttribute("client") @Valid BankClientDto bankClient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "client/create";
        }
        try {
            userService.createClient(bankClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
