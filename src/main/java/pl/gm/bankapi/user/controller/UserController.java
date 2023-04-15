package pl.gm.bankapi.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import pl.gm.bankapi.client.dto.BankClientDto;
import pl.gm.bankapi.user.dto.UserDto;
import pl.gm.bankapi.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    @GetMapping("/all")
    @ResponseBody
    public UserDto getAllUsers() {
        return userService.findByName("aaa");
    }
}
