package pl.gm.bankapi.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.account.model.BankAccount;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {

    @Query("SELECT b FROM BankAccount b WHERE b.client.id = :clientId")
    List<BankAccount> getAccountsByClientId(@Param("clientId") Long clientId);

    @Query("SELECT b FROM BankAccount b JOIN b.client c JOIN c.user u WHERE u.username = :username")
    List<BankAccount> getAccountsByUsername(@Param("username") String username);
}
