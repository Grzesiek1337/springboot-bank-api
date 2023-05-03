package pl.gm.bankapi.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.client.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("SELECT c FROM Client c JOIN c.user u WHERE u.username = :username")
    Client getClientByUsername(@Param("username") String username);
}
