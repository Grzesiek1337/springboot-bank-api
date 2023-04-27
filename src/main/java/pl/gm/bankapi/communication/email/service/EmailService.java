package pl.gm.bankapi.communication.email.service;

public interface EmailService {

    void sendSimpleMessage(String to,
                           String subject,
                           String text);
}

