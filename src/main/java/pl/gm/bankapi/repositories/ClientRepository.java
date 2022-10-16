package pl.gm.bankapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
}
