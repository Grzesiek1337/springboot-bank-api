package pl.gm.bankapi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gm.bankapi.communication.notification.dto.NotificationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<RoleDto> roles;
    private List<NotificationDto> notifications = new ArrayList<>();
}
