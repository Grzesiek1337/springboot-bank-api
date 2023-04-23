package pl.gm.bankapi.admin.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.gm.bankapi.client.service.ClientService;
import pl.gm.bankapi.user.currentuser.CurrentUserDetails;
import pl.gm.bankapi.user.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ClientService clientService;

    public AdminController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping
    public String getPanel() {
        return "admin/panel";
    }

    @GetMapping("/users/all")
    public String getAllUsers(Model model) {
        model.addAttribute("usersDto", userService.findAll());
        return "user/list";
    }

    @GetMapping("/users/delete/{userId}")
    @ResponseBody
    public String getAllUsers(@PathVariable("userId") Long userId, @AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        if (userId.equals(currentUserDetails.getUser().getId())) {
            return "U cant remove yourself.";
        }
        clientService.delete(userId);
        userService.delete(userId);
        return "User with id :: " + userId + " has been removed.";
    }
}
