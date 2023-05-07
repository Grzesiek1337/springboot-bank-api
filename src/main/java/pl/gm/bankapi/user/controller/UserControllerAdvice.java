package pl.gm.bankapi.user.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.gm.bankapi.communication.notification.dto.NotificationDto;
import pl.gm.bankapi.user.currentuser.CurrentUserDetails;
import pl.gm.bankapi.user.dto.UserDto;
import pl.gm.bankapi.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 Controller advice that handles user-related exceptions and provides user notifications to views.
 */
@ControllerAdvice
public class UserControllerAdvice {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserControllerAdvice(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    /**
     Retrieves a list of user notifications and adds it as a model attribute to be used in views.
     @param currentUserDetails the currently logged in user
     @return a list of user notifications
     */
    @ModelAttribute("userNotifications")
    public List<NotificationDto> getUserNotifications(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        if(currentUserDetails == null) {
            return new ArrayList<>();
        }
        UserDto user = userService.findByName(currentUserDetails.getUsername());
        return user.getNotifications();
    }
}