package com.bottomline.fm.moneytransfer.service.spi;

import com.bottomline.fm.moneytransfer.model.Account;
import com.bottomline.fm.moneytransfer.model.AccountOwner;

import java.math.BigDecimal;

public interface AccountService {
    Account findAccountByNumber(String accountNumber);
    Account createAccount(AccountOwner accountOwner, String currency);
    Account createAccount(AccountOwner accountOwner, String currency, BigDecimal initialBalance);
    Account updateAccount(Account account);
}
