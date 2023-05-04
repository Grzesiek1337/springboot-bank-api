package pl.gm.bankapi.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.loan.model.LoanEntity;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity,Long> {
}
