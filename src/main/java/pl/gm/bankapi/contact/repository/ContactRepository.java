package pl.gm.bankapi.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.contact.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
}
