package pl.gm.bankapi.communication.contact.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.communication.contact.model.Contact;
import pl.gm.bankapi.communication.contact.repository.ContactRepository;
import pl.gm.bankapi.communication.contact.dto.ContactDto;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public ContactService(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    public void saveContact(ContactDto contactDto) {
        Contact contact = modelMapper.map(contactDto, Contact.class);
        contact.setResponseProvided(false);
        contactRepository.save(contact);
    }

    public void generateSomeContacts(int number) {
        for (int i = 0; i < number; i++) {
            Contact contact = new Contact();
            contact.setName("Little Amy");
            contact.setEmail("someone@wholoveme.pl");
            contact.setMessage("Some message,question.");
            contact.setSubject("How to something.");
            contact.setResponseProvided(false);
            contactRepository.save(contact);
        }
    }
}
