package pl.gm.bankapi.account.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.account.dto.BankAccountDto;
import pl.gm.bankapi.account.model.BankAccount;
import pl.gm.bankapi.account.repository.BankAccountRepository;

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

    public List<BankAccountDto> getAccountsByClientId(Long id) {
        List<BankAccount> accounts = bankAccountRepository.getAccountsByClientId(id);
        return accounts.stream()
                .map(account -> modelMapper.map(account, BankAccountDto.class))
                .collect(Collectors.toList());
    }

    public List<BankAccountDto> getAccountsByUsername(String username) {
        List<BankAccount> accounts = bankAccountRepository.getAccountsByUsername(username);
        return accounts.stream()
                .map(account -> modelMapper.map(account, BankAccountDto.class))
                .collect(Collectors.toList());
    }



}
