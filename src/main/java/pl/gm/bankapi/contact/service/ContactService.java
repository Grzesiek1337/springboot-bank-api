package pl.gm.bankapi.contact.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.contact.dto.ContactDto;
import pl.gm.bankapi.contact.model.Contact;
import pl.gm.bankapi.contact.repository.ContactRepository;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public ContactService(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    public void saveContact(ContactDto contactDto) {
        Contact contact = modelMapper.map(contactDto,Contact.class);
        contactRepository.save(contact);
    }

}
