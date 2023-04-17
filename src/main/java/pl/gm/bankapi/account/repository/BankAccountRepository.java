package pl.gm.bankapi.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.gm.bankapi.account.model.BankAccount;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {

    /**
     * Retrieves all bank accounts belonging to the client with the specified ID.
     *
     * @param clientId the ID of the client
     * @return a list of bank accounts belonging to the client
     */
    @Query("SELECT b FROM BankAccount b WHERE b.client.id = :clientId")
    List<BankAccount> getAccountsByClientId(@Param("clientId") Long clientId);

    /**
     * Retrieves all bank accounts belonging to the user with the specified username.
     *
     * @param username the username of the user
     * @return a list of bank accounts belonging to the user
     */
    @Query("SELECT b FROM BankAccount b JOIN b.client c JOIN c.user u WHERE u.username = :username")
    List<BankAccount> getAccountsByUsername(@Param("username") String username);

    /**
     * Retrieves the bank account with the specified account number.
     *
     * @param accountNumber the account number of the bank account
     * @return the bank account with the specified account number
     */
    @Query("SELECT b FROM BankAccount b WHERE b.accountNumber = :accountNumber")
    BankAccount findByAccountNumber(@Param("accountNumber") String accountNumber);
}
