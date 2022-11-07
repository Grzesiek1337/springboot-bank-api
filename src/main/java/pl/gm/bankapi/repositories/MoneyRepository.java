package pl.gm.bankapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.model.Money;

@Repository
public interface MoneyRepository extends JpaRepository<Money,Long> {
}
