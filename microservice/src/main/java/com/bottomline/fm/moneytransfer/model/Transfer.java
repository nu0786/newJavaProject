package com.bottomline.fm.moneytransfer.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transfer {

    private Instant valueDate;

    private Account fromAccount;

    private Account toAccount;

    private BigDecimal amount;
    
    private String currency;

    private String transferLabel;

    private String approvedBy;

    public Transfer() {
    }

    public Transfer(Instant valueDate, Account fromAccount, Account toAccount, BigDecimal amount, String currency) {
        this.valueDate = valueDate;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.currency = currency;
    }

    public Instant getValueDate() {
        return valueDate;
    }

    public void setValueDate(Instant valueDate) {
        this.valueDate = valueDate;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransferLabel() {
        return transferLabel;
    }

    public void setTransferLabel(String transferLabel) {
        this.transferLabel = transferLabel;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
}
