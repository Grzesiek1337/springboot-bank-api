package pl.gm.bankapi.communication.feedback;

import lombok.Data;
import pl.gm.bankapi.communication.contact.dto.ContactDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class ContactFeedbackDto {

    private Long id;
    @NotBlank
    @Size(max = 1000)
    private String message;
    private String author;
    private LocalDateTime responseTime;
    private ContactDto contact;
}
