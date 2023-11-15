package com.bottomline.fm.moneytransfer.repository.spi;

import com.bottomline.fm.moneytransfer.model.Account;

import java.util.Optional;

public interface AccountRepositoryService {

    Account create(Account account);
    Account update(Account account);

    Optional<Account> findById(Long id);
    Optional<Account> findAccountByNumber(String accountNumber);
}
