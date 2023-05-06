package pl.gm.bankapi.notification.cleaner;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.gm.bankapi.notification.service.NotificationService;

/**
 * A component that schedules the cleanup of old notifications every Sunday at midnight.
 */
@Component
public class NotificationCleanupScheduler {

    private final NotificationService notificationService;

    public NotificationCleanupScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Scheduled method that runs every Sunday at midnight (00:00)
    @Scheduled(cron = "0 0 0 * * SUN")
    public void cleanUpOldNotifications() {
        // Delete notifications that are older than one week
        notificationService.deleteNotificationsOlderThanOneWeek();
    }
}
