package pl.gm.bankapi.notification.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gm.bankapi.notification.service.NotificationService;
import pl.gm.bankapi.user.currentuser.CurrentUserDetails;
import pl.gm.bankapi.user.model.User;


@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/list")
    public String getUserNotifications(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        User currentUser = currentUserDetails.getUser();
        model.addAttribute("notifications",notificationService.getNotificationsForUser(currentUser));
        return "notification/list";
    }
}
