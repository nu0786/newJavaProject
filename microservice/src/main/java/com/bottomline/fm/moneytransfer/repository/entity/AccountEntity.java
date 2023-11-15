package com.bottomline.fm.moneytransfer.repository.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
@Table(name = "tb_account")
@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name="account_generator", sequenceName = "account_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_owner_full_name")
    private String accountOwnerFullName;

    @Column(name = "account_owner_phone_number")
    private String accountOwnerPhoneNumber;

    @Column(name = "account_owner_email")
    private String accountOwnerEmail;

    @Column(name = "account_owner_birthdate")
    private String accountOwnerBirthdate;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "fromAccount", cascade = {CascadeType.ALL})
    private List<TransferEntity> receivedTransfer;

    @OneToMany(mappedBy = "toAccount", cascade = {CascadeType.ALL})
    private List<TransferEntity> sendTransfer;


    @PrePersist
    private void beforeSave() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    private void beforeUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAccountOwnerFullName() {
        return accountOwnerFullName;
    }

    public void setAccountOwnerFullName(String accountOwnerFullName) {
        this.accountOwnerFullName = accountOwnerFullName;
    }

    public String getAccountOwnerPhoneNumber() {
        return accountOwnerPhoneNumber;
    }

    public void setAccountOwnerPhoneNumber(String accountOwnerPhoneNumber) {
        this.accountOwnerPhoneNumber = accountOwnerPhoneNumber;
    }

    public String getAccountOwnerEmail() {
        return accountOwnerEmail;
    }

    public void setAccountOwnerEmail(String accountOwnerEmail) {
        this.accountOwnerEmail = accountOwnerEmail;
    }

    public String getAccountOwnerBirthdate() {
        return accountOwnerBirthdate;
    }

    public void setAccountOwnerBirthdate(String accountOwnerBirthdate) {
        this.accountOwnerBirthdate = accountOwnerBirthdate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<TransferEntity> getReceivedTransfer() {
        return receivedTransfer;
    }

    public void setReceivedTransfer(List<TransferEntity> receivedTransfer) {
        this.receivedTransfer = receivedTransfer;
    }

    public List<TransferEntity> getSendTransfer() {
        return sendTransfer;
    }

    public void setSendTransfer(List<TransferEntity> sendTransfer) {
        this.sendTransfer = sendTransfer;
    }

}
