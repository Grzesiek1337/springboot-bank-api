package pl.gm.bankapi.account.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.account.dto.BankAccountDto;
import pl.gm.bankapi.account.model.BankAccount;
import pl.gm.bankapi.account.repository.BankAccountRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final ModelMapper modelMapper;

    public BankAccountService(BankAccountRepository bankAccountRepository, ModelMapper modelMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves a list of bank accounts associated with the given client ID.
     *
     * @param id the client ID
     * @return a list of bank account DTOs
     */
    public List<BankAccountDto> getAccountsByClientId(Long id) {
        List<BankAccount> accounts = bankAccountRepository.getAccountsByClientId(id);
        return accounts.stream()
                .map(account -> modelMapper.map(account, BankAccountDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of bank accounts associated with the given username.
     *
     * @param username the username
     * @return a list of bank account DTOs
     */
    public List<BankAccountDto> getAccountsByUsername(String username) {
        List<BankAccount> accounts = bankAccountRepository.getAccountsByUsername(username);
        return accounts.stream()
                .map(account -> modelMapper.map(account, BankAccountDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a bank account with the given ID.
     *
     * @param id the bank account ID
     * @return the bank account DTO
     * @throws EntityNotFoundException if the bank account is not found
     */
    public BankAccountDto getAccountById(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with ID: " + id));
        return modelMapper.map(bankAccount, BankAccountDto.class);
    }
}
