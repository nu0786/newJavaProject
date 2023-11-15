package com.bottomline.fm.moneytransfer.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "tb_transfer")
@Entity
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transfer_generator")
    @SequenceGenerator(name="transfer_generator", sequenceName = "transfer_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "value_date")
    private Instant valueDate;

    @JoinColumn(name = "from_account", nullable = false, referencedColumnName = "id")
    @ManyToOne
    private AccountEntity fromAccount;

    @JoinColumn(name = "to_account", nullable = false, referencedColumnName = "id")
    @ManyToOne
    private AccountEntity toAccount;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "label")
    private String label;

    @Column(name = "approved_by")
    private String approvedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getValueDate() {
        return valueDate;
    }

    public void setValueDate(Instant valueDate) {
        this.valueDate = valueDate;
    }

    public AccountEntity getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(AccountEntity fromAccount) {
        this.fromAccount = fromAccount;
    }

    public AccountEntity getToAccount() {
        return toAccount;
    }

    public void setToAccount(AccountEntity toAccount) {
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public TransferEntity() {
    }
    public TransferEntity(Instant valueDate, String fromAccountNumber, String toAccountNumber, BigDecimal amount, String currency) {
        super();
        setValueDate(valueDate);
        setFromAccount(new AccountEntity());
        getFromAccount().setAccountNumber(fromAccountNumber);
        setToAccount(new AccountEntity());
        getToAccount().setAccountNumber(toAccountNumber);
        setAmount(amount);
        setCurrency(currency);
    }
    public TransferEntity(Instant valueDate, AccountEntity fromAccount, AccountEntity toAccount, BigDecimal amount, String currency) {
        super();
        setValueDate(valueDate);
        setFromAccount(fromAccount);
        setToAccount(toAccount);
        setAmount(amount);
        setCurrency(currency);
    }
}
