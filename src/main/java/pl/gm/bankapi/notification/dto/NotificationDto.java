package pl.gm.bankapi.notification.dto;

import lombok.Data;
import pl.gm.bankapi.user.dto.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 Data Transfer Object (DTO) for notifications.
 */
@Data
public class NotificationDto {

    private Long id;
    @NotBlank
    @Size(max = 200)
    private String message;
    private UserDto userDto;
    private String createdAt;
}
