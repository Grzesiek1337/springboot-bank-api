package pl.gm.bankapi;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import pl.gm.bankapi.generator.BankAccountNumberGenerator;
import pl.gm.bankapi.model.Money;
import pl.gm.bankapi.model.StandardAccount;
import pl.gm.bankapi.repositories.StandardAccountRepository;
import pl.gm.bankapi.service.MoneyService;

import javax.validation.Validator;
import java.math.BigDecimal;

@SpringBootApplication
@ComponentScan("pl.gm")
public class BankApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BankApiApplication.class, args);
	}

	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Autowired
	private MoneyService moneyService;
	@Autowired
	private StandardAccountRepository standardAccountRepository;

	@Override
	public void run(String... args) throws Exception {
		StandardAccount standardAccount = new StandardAccount();
		standardAccount.setAccountType("Standard");
		standardAccount.setAccountNumber(BankAccountNumberGenerator.createAccountNumber());
		Money money = new Money();
		money.setId(null);
		money.setAmount(new BigDecimal("0.00"));
		money.setStandardAccount(standardAccount);
		money.setStandardAccount(standardAccount);
		standardAccount.setAccountMoney(money);
		standardAccountRepository.save(standardAccount);
		moneyService.save(money);


	}
}
