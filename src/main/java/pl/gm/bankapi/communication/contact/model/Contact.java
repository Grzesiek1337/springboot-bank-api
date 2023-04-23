package pl.gm.bankapi.communication.contact.model;

import lombok.Data;

import javax.persistence.*;
@Entity
@Table(name = "contacts")
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String subject;

    @Column(length = 1000)
    private String message;

    private boolean isResponseProvided;
}
