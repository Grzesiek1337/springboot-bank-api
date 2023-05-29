package pl.gm.bankapi.communication.notification.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.gm.bankapi.communication.notification.dto.NotificationDto;
import pl.gm.bankapi.communication.notification.service.NotificationService;
import pl.gm.bankapi.user.currentuser.CurrentUserDetails;
import pl.gm.bankapi.user.model.User;

import javax.validation.Valid;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * GET request mapping to display the list of notifications for the current user.
     * @param currentUserDetails The currently authenticated user's details.
     * @param model The model to add attributes to.
     * @return The name of the Thymeleaf template to render.
     */
    @GetMapping("/list")
    public String getUserNotifications(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        User currentUser = currentUserDetails.getUser();
        model.addAttribute("notifications",notificationService.getNotificationsForUser(currentUser));
        return "notification/list";
    }

    /**
     * GET request mapping to display the form for sending a notification to all users.
     * @param model The model to add attributes to.
     * @return The name of the Thymeleaf template to render.
     */
    @GetMapping("/send-to-all")
    public String sendNotificationToAllForm(Model model) {
        model.addAttribute("notification",new NotificationDto());
        return "notification/send-to-all";
    }

    /**
     * POST request mapping to submit a notification to be sent to all users.
     * @param notificationDto The NotificationDto object containing the notification message.
     * @param bindingResult The BindingResult object to hold errors.
     * @param model The model to add attributes to.
     * @return A redirect to the admin page if successful, or the notification form with errors if not.
     */
    @PostMapping("/send-to-all")
    public String submitNotificationToAllForm(
            @Valid @ModelAttribute("notification") NotificationDto notificationDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Invalid notification data");
            model.addAttribute("notification", notificationDto);
            return "notification/send-to-all";
        }
        notificationService.sendNotificationToAllUsers(notificationDto);
        return "redirect:/admin";
    }

    /**
     * This method handles the request to delete a notification with the given ID.
     * After deleting the notification, it redirects the user to the notifications list page.
     * @param id the ID of the notification to delete
     * @return a redirect to the notifications list page
     */
    @GetMapping("/delete/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotificationById(id);
        return "redirect:/notifications/list";
    }
}
