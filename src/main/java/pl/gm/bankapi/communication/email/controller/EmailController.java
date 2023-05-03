package pl.gm.bankapi.communication.email.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gm.bankapi.communication.email.service.EmailService;
import pl.gm.bankapi.communication.email.service.EmailServiceImpl;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

}
