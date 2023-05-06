package pl.gm.bankapi.notification.model;

import lombok.Data;
import pl.gm.bankapi.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "notifications")
@Data
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String createdAt;
}
