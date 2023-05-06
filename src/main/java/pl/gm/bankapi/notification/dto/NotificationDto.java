package pl.gm.bankapi.notification.dto;

import lombok.Data;
import pl.gm.bankapi.user.dto.UserDto;

/**
 Data Transfer Object (DTO) for notifications.
 */
@Data
public class NotificationDto {

    private Long id;
    private String message;
    private UserDto userDto;
    private String createdAt;
}
