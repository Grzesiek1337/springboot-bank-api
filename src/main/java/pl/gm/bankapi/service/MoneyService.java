package pl.gm.bankapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.model.Money;
import pl.gm.bankapi.repositories.MoneyRepository;


@Service
public class MoneyService {

    @Autowired
    private MoneyRepository moneyRepository;

    public void save(Money money) {
        moneyRepository.save(money);
    }

}
