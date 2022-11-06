package pl.gm.bankapi.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.model.StandardAccount;
import pl.gm.bankapi.repositories.StandardAccountRepository;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class BankAccountService {

    private StandardAccountRepository standardAccountRepository;
    private ModelMapper modelMapper;

    @Transactional
    public void saveStandardAccount(StandardAccount standardAccount) {
        standardAccountRepository.save(standardAccount);
    }

}
