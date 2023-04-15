package pl.gm.bankapi.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.client.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

}
