package pl.gm.bankapi.communication.contact.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.communication.contact.model.Contact;
import pl.gm.bankapi.communication.contact.repository.ContactRepository;
import pl.gm.bankapi.communication.contact.dto.ContactDto;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public ContactService(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates a new contact.
     */
    public void createContact(ContactDto contactDto) {
        Contact contact = modelMapper.map(contactDto, Contact.class);
        contact.setResponseProvided(false);
        contactRepository.save(contact);
    }

    /**
     * Updates an existing contact.
     */
    public void updateExistingContact(ContactDto contactDto) {
        Contact contact = modelMapper.map(contactDto, Contact.class);
        contactRepository.save(contact);
    }

    /**
     * Returns a list of all contacts.
     */
    public List<ContactDto> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .map(contact -> modelMapper.map(contact, ContactDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of all unanswered contacts.
     */
    public List<ContactDto> findAllUnansweredContacts() {
        List<Contact> contacts = contactRepository.findAllUnansweredContacts();
        return contacts.stream()
                .map(contact -> modelMapper.map(contact, ContactDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of all answered contacts.
     */
    public List<ContactDto> findAllAnsweredContacts() {
        List<Contact> contacts = contactRepository.findAllAnsweredContacts();
        return contacts.stream()
                .map(contact -> modelMapper.map(contact, ContactDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Returns the contact with the specified ID.
     * @throws EntityNotFoundException if the contact is not found
     */
    public ContactDto getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id " + id));
        return modelMapper.map(contact, ContactDto.class);
    }

    /**
     * Generates a specified number of dummy contacts for testing purposes.
     */
    public void generateDummyContacts(int number) {
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
