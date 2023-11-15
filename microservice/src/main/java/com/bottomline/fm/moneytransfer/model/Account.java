package com.bottomline.fm.moneytransfer.model;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

    private String id;
    private String currency;
    private BigDecimal balance;
    private String accountNumber;

    private AccountOwner accountOwner;

    private Instant createdAt;
    private Instant updateDate;

    private List<Transfer> receivedTransfer;
    private List<Transfer> sentTransfer;

    public Account(AccountOwner accountOwner, String accountNumber, String currency, BigDecimal balance) {
        this.accountOwner = accountOwner;
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountOwner getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(AccountOwner accountOwner) {
        this.accountOwner = accountOwner;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public List<Transfer> getReceivedTransfer() {
        return receivedTransfer;
    }

    public void setReceivedTransfer(List<Transfer> receivedTransfer) {
        this.receivedTransfer = receivedTransfer;
    }

    public List<Transfer> getSentTransfer() {
        return sentTransfer;
    }

    public void setSentTransfer(List<Transfer> sentTransfer) {
        this.sentTransfer = sentTransfer;
    }
}
