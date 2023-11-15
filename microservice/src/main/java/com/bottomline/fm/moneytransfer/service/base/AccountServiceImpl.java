package com.bottomline.fm.moneytransfer.service.base;

import com.bottomline.fm.moneytransfer.exception.NotFoundException;
import com.bottomline.fm.moneytransfer.model.Account;
import com.bottomline.fm.moneytransfer.model.AccountOwner;
import com.bottomline.fm.moneytransfer.repository.spi.AccountRepositoryService;
import com.bottomline.fm.moneytransfer.service.spi.AccountNumberGenerator;
import com.bottomline.fm.moneytransfer.service.spi.AccountService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    protected final AccountNumberGenerator accountNumberGenerator;
    protected final AccountRepositoryService accountRepositoryService;
    protected final String accountNumberSeed;

    public AccountServiceImpl(@Qualifier("accountNumberGenerator") AccountNumberGenerator accountNumberGenerator,
                              @Qualifier("accountRepositoryService") AccountRepositoryService accountRepositoryService) {
        this.accountNumberGenerator = accountNumberGenerator;
        this.accountRepositoryService = accountRepositoryService;
        // Account number seed is unique per instance of this service. It changes at every restart
        this.accountNumberSeed = new Random().ints(97, 122).limit(4)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    @Override
    public Account findAccountByNumber(String accountNumber) {
        return accountRepositoryService.findAccountByNumber(accountNumber).orElseThrow(() -> new NotFoundException(String.format("Account with number %s was not found", accountNumber)));
    }

    @Override
    public Account createAccount(AccountOwner accountOwner, String currency) {
        return createAccount(accountOwner, currency, BigDecimal.ZERO);
    }

    @Override
    public Account createAccount(AccountOwner accountOwner, String currency, BigDecimal initialBalance) {
        Integer accountNumber = accountNumberGenerator.generate();
        return accountRepositoryService.create(new Account(accountOwner, accountNumberSeed + "-" + accountNumber + "-" + LocalDate.now(), currency, initialBalance));
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepositoryService.update(account);
    }
}
