package com.bottomline.fm.moneytransfer.controller;

import com.bottomline.fm.moneytransfer.IntegrationTestClass;
import com.bottomline.fm.moneytransfer.model.Account;
import com.bottomline.fm.moneytransfer.model.AccountOwner;
import com.bottomline.fm.moneytransfer.model.Transfer;
import com.bottomline.fm.moneytransfer.repository.base.AccountRepository;
import com.bottomline.fm.moneytransfer.repository.base.TransferRepository;
import com.bottomline.fm.moneytransfer.repository.entity.AccountEntity;
import com.bottomline.fm.moneytransfer.repository.mapper.AccountMapper;
import com.bottomline.fm.moneytransfer.repository.mapper.CycleAvoidingMappingContext;
import com.bottomline.fm.moneytransfer.service.spi.TransferService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

public class TransferServiceIT extends IntegrationTestClass {
    @LocalServerPort
    private String port;

    @Autowired
    protected TransferService transferService;
    @Autowired
    protected TransferRepository transferRepository;
    @Autowired
    protected AccountRepository accountRepository;
    protected AccountMapper accountMapper = AccountMapper.INSTANCE;

    protected TransferService getTransferService() {
        return transferService;
    }

    @BeforeEach
    public void setup() {
        transferRepository.deleteAll();
    }

    @Test
    public void performTransferWithoutApproval_shouldReturnTrue_whenBusinessRequirementAreFulfill() {
        // Given
        String fromAccountNumber = UUID.randomUUID().toString();
        String toAccountNumber = UUID.randomUUID().toString();
        AccountOwner fromAccountOwner = new AccountOwner("From Account", "example@example.com", "+7998964874", "1987-09-23");
        Account fromAccount = new Account(fromAccountOwner, fromAccountNumber, "CHF", BigDecimal.valueOf(20000));
        AccountOwner toAccountOwner = new AccountOwner("To Account", "example@example.com", "+7998964874", "1987-09-23");
        Account toAccount = new Account(toAccountOwner, toAccountNumber, "CHF", BigDecimal.ZERO);
        accountRepository.save(accountMapper.toEntity(fromAccount, new CycleAvoidingMappingContext()));
        accountRepository.save(accountMapper.toEntity(toAccount, new CycleAvoidingMappingContext()));
        // When
        Optional<Transfer> transferPerformed = getTransferService().performTransferWithoutApproval(fromAccountNumber, toAccountNumber, "CHF", BigDecimal.valueOf(2000));
        // Then
        assertThat(transferPerformed).isPresent();
        AccountEntity updatedToAccount = accountRepository.findByAccountNumber(toAccountNumber).get();
        assertThat(updatedToAccount.getBalance()).isEqualByComparingTo(BigDecimal.valueOf(2000));
        AccountEntity updatedFromAccount = accountRepository.findByAccountNumber(fromAccountNumber).get();
        assertThat(updatedFromAccount.getBalance()).isEqualByComparingTo(BigDecimal.valueOf(18000));
    }

    @Test
    public void performTransferWithoutApproval_shouldReturnTrue_whenBusinessRequirementAreFulfill_andExecuteInParallel() {
        // Given
        String fromAccountNumber = UUID.randomUUID().toString();
        String toAccountNumber = UUID.randomUUID().toString();
        AccountOwner fromAccountOwner = new AccountOwner("From Account", "example@example.com", "+7998964874", "1987-09-23");
        Account fromAccount = new Account(fromAccountOwner, fromAccountNumber, "CHF", BigDecimal.valueOf(20000));
        AccountOwner toAccountOwner = new AccountOwner("To Account", "example@example.com", "+7998964874", "1987-09-23");
        Account toAccount = new Account(toAccountOwner, toAccountNumber, "CHF", BigDecimal.ZERO);
        accountRepository.save(accountMapper.toEntity(fromAccount, new CycleAvoidingMappingContext()));
        accountRepository.save(accountMapper.toEntity(toAccount, new CycleAvoidingMappingContext()));
        // When
        int concurrency = 8;
        List<CompletableFuture<Void>> threads = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(concurrency);
        for(int i = 0; i <concurrency; i++) {
            threads.add(CompletableFuture.runAsync(() -> {
                try {
                    countDownLatch.await();
                    getTransferService().performTransferWithoutApproval(fromAccountNumber, toAccountNumber, "CHF", BigDecimal.valueOf(5000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
            countDownLatch.countDown();
        }
        CompletableFuture.allOf(threads.toArray(new CompletableFuture[0])).join();
        // Then
//        assertThat(transferService.numberOfTransfersForAccount(fromAccountNumber)).isEqualTo(4);
        AccountEntity updatedToAccount = accountRepository.findByAccountNumber(toAccountNumber).get();
        assertThat(updatedToAccount.getBalance()).isEqualByComparingTo(BigDecimal.valueOf(20000));
        AccountEntity updatedFromAccount = accountRepository.findByAccountNumber(fromAccountNumber).get();
        assertThat(updatedFromAccount.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    public void performTransferWithoutApproval_shouldReturnFalse_whenFromAccountBalanceCouldBeLessThan0() {
        // Given
        String fromAccountNumber = UUID.randomUUID().toString();
        String toAccountNumber = UUID.randomUUID().toString();
        AccountOwner fromAccountOwner = new AccountOwner("From Account", "example@example.com", "+7998964874", "1987-09-23");
        Account fromAccount = new Account(fromAccountOwner, fromAccountNumber, "CHF", BigDecimal.valueOf(20000));
        AccountOwner toAccountOwner = new AccountOwner("To Account", "example@example.com", "+7998964874", "1987-09-23");
        Account toAccount = new Account(toAccountOwner, toAccountNumber, "CHF", BigDecimal.ZERO);
        accountRepository.save(accountMapper.toEntity(fromAccount, new CycleAvoidingMappingContext()));
        accountRepository.save(accountMapper.toEntity(toAccount, new CycleAvoidingMappingContext()));
        // When
        Optional<Transfer> transferPerformed = getTransferService().performTransferWithoutApproval(fromAccountNumber, toAccountNumber, "CHF", BigDecimal.valueOf(20001));
        // Then
        assertThat(transferPerformed).isEmpty();
        AccountEntity updatedToAccount = accountRepository.findByAccountNumber(toAccountNumber).get();
        assertThat(updatedToAccount.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
        AccountEntity updatedFromAccount = accountRepository.findByAccountNumber(fromAccountNumber).get();
        assertThat(updatedFromAccount.getBalance()).isEqualByComparingTo(BigDecimal.valueOf(20000));
    }


    @Test
    public void topSenderAccounts_shouldReturnAccountNumberSortedByThereNumberOfTransferToday() {
        // Given
        String toAccountNumber = UUID.randomUUID().toString();
        AccountOwner fromAccountOwner = new AccountOwner("From Account", "example@example.com", "+7998964874", "1987-09-23");
        Account expected1Account = new Account(fromAccountOwner, UUID.randomUUID().toString(), "CHF", BigDecimal.valueOf(20000));
        Account expected2Account = new Account(fromAccountOwner, UUID.randomUUID().toString(), "CHF", BigDecimal.valueOf(20000));
        Account expected3Account = new Account(fromAccountOwner, UUID.randomUUID().toString(), "CHF", BigDecimal.valueOf(20000));
        Account expected4Account = new Account(fromAccountOwner, UUID.randomUUID().toString(), "CHF", BigDecimal.valueOf(20000));
        AccountOwner toAccountOwner = new AccountOwner("To Account", "example@example.com", "+7998964874", "1987-09-23");
        Account toAccount = new Account(toAccountOwner, toAccountNumber, "CHF", BigDecimal.ZERO);
        accountRepository.save(accountMapper.toEntity(expected1Account, new CycleAvoidingMappingContext()));
        accountRepository.save(accountMapper.toEntity(expected2Account, new CycleAvoidingMappingContext()));
        accountRepository.save(accountMapper.toEntity(expected3Account, new CycleAvoidingMappingContext()));
        accountRepository.save(accountMapper.toEntity(expected4Account, new CycleAvoidingMappingContext()));
        accountRepository.save(accountMapper.toEntity(toAccount, new CycleAvoidingMappingContext()));
        for(int i = 0; i < 4; i ++) {
            getTransferService().performTransferWithoutApproval(expected2Account.getAccountNumber(), toAccountNumber, "CHF", BigDecimal.valueOf(1));
        }
        for(int i = 0; i < 10; i ++) {
            getTransferService().performTransferWithoutApproval(expected1Account.getAccountNumber(), toAccountNumber, "CHF", BigDecimal.valueOf(1));
        }
        for(int i = 0; i < 2; i ++) {
            getTransferService().performTransferWithoutApproval(expected4Account.getAccountNumber(), toAccountNumber, "CHF", BigDecimal.valueOf(1));
        }
        for(int i = 0; i < 3; i ++) {
            getTransferService().performTransferWithoutApproval(expected3Account.getAccountNumber(), toAccountNumber, "CHF", BigDecimal.valueOf(1));
        }
        for (int i = 0; i < 100; i++) {
            String fromAccountNumber = UUID.randomUUID().toString();
            Account fromAccount = new Account(fromAccountOwner, fromAccountNumber, "CHF", BigDecimal.valueOf(20000));
            accountRepository.save(accountMapper.toEntity(fromAccount, new CycleAvoidingMappingContext()));
            Optional<Transfer> transferPerformed = transferService.performTransferWithoutApproval(fromAccountNumber, toAccountNumber, "CHF", BigDecimal.valueOf(1));
            AssertionsForClassTypes.assertThat(transferPerformed).isPresent();
        }
        // When
        List<String> topSenderAccounts = transferService.topSenderAccounts();
        // Then
        assertThat(topSenderAccounts.size()).isGreaterThan(4);
        assertThat(topSenderAccounts.get(0)).isEqualTo(expected1Account.getAccountNumber());
        assertThat(topSenderAccounts.get(1)).isEqualTo(expected2Account.getAccountNumber());
        assertThat(topSenderAccounts.get(2)).isEqualTo(expected3Account.getAccountNumber());
        assertThat(topSenderAccounts.get(3)).isEqualTo(expected4Account.getAccountNumber());
    }
}
