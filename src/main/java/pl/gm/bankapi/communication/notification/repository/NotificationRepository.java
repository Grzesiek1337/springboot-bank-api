package pl.gm.bankapi.communication.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.communication.notification.model.NotificationEntity;
import pl.gm.bankapi.user.model.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {

    // Returns all notifications for a given user, ordered by creation date in descending order
    List<NotificationEntity> findByUserOrderByCreatedAtDesc(User user);

}
