package pl.gm.bankapi.communication.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.communication.contact.model.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

    @Query("SELECT c FROM Contact c WHERE c.isResponseProvided = false")
    List<Contact> findAllUnansweredContacts();

    @Query("SELECT c FROM Contact c WHERE c.isResponseProvided = true")
    List<Contact> findAllAnsweredContacts();
}
