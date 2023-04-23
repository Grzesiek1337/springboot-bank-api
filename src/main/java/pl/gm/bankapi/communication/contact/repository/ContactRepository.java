package pl.gm.bankapi.communication.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.communication.contact.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
}
