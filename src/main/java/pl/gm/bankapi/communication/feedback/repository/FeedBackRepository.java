package pl.gm.bankapi.communication.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gm.bankapi.communication.feedback.model.ContactFeedbackEntity;

public interface FeedBackRepository extends JpaRepository<ContactFeedbackEntity,Long> {
}
