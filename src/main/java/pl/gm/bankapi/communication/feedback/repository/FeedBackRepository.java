package pl.gm.bankapi.communication.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.communication.feedback.model.ContactFeedbackEntity;

@Repository
public interface FeedBackRepository extends JpaRepository<ContactFeedbackEntity,Long> {
}
