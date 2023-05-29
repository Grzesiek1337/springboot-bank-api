package pl.gm.bankapi.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.payment.model.PaymentScheduleEntity;

@Repository
public interface PaymentScheduleRepository extends JpaRepository<PaymentScheduleEntity,Long> {

}