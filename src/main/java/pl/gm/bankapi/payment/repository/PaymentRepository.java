package pl.gm.bankapi.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.payment.model.PaymentEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity,Long> {

}