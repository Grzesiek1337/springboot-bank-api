package pl.gm.bankapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.model.StandardAccount;

@Repository
public interface StandardAccountRepository extends JpaRepository<StandardAccount,Long> {
}
