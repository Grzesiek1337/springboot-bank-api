package pl.gm.bankapi.communication.feedback.model;

import lombok.Data;
import pl.gm.bankapi.communication.contact.model.Contact;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_feedbacks")
@Data
public class ContactFeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private String author;

    private LocalDateTime responseTime;

    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
}
