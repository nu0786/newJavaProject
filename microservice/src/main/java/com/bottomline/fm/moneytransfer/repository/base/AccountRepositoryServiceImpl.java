package com.bottomline.fm.moneytransfer.repository.base;

import com.bottomline.fm.moneytransfer.exception.BadRequestException;
import com.bottomline.fm.moneytransfer.model.Account;
import com.bottomline.fm.moneytransfer.repository.mapper.AccountMapper;
import com.bottomline.fm.moneytransfer.repository.mapper.CycleAvoidingMappingContext;
import com.bottomline.fm.moneytransfer.repository.spi.AccountRepositoryService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Component("accountRepositoryService")
public class AccountRepositoryServiceImpl implements AccountRepositoryService {

    protected final AccountRepository accountRepository;
    protected final AccountMapper accountMapper = AccountMapper.INSTANCE;

    @Autowired
    public AccountRepositoryServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account create(Account account) {
        try {
            return accountMapper.toModel(accountRepository.save(accountMapper.toEntity(account, new CycleAvoidingMappingContext())), new CycleAvoidingMappingContext());
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException
                && e.getCause() instanceof ConstraintViolationException
                && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException
            ) {
                SQLIntegrityConstraintViolationException cause = ((SQLIntegrityConstraintViolationException) e.getCause().getCause());
                if (cause.getSQLState().equals("23505")) { // Duplicate key violation
                    throw new BadRequestException("Account number is already taken");
                }
            }
            throw e;
        }
    }

    @Override
    public Account update(Account account) {
        return accountMapper.toModel(accountRepository.save(accountMapper.toEntity(account, new CycleAvoidingMappingContext())), new CycleAvoidingMappingContext());
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id).map(accountEntity -> accountMapper.toModel(accountEntity, new CycleAvoidingMappingContext()));
    }

    @Override
    public Optional<Account> findAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).map(accountEntity -> accountMapper.toModel(accountEntity, new CycleAvoidingMappingContext()));
    }
}
