package com.bottomline.fm.moneytransfer.repository.mapper;

import com.bottomline.fm.moneytransfer.model.Account;
import com.bottomline.fm.moneytransfer.model.AccountOwner;
import com.bottomline.fm.moneytransfer.model.Transfer;
import com.bottomline.fm.moneytransfer.repository.entity.AccountEntity;
import com.bottomline.fm.moneytransfer.repository.entity.TransferEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.17 (Eclipse Adoptium)"
)
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountEntity toEntity(Account account, CycleAvoidingMappingContext context) {
        AccountEntity target = context.getMappedInstance( account, AccountEntity.class );
        if ( target != null ) {
            return target;
        }

        if ( account == null ) {
            return null;
        }

        AccountEntity accountEntity = new AccountEntity();

        context.storeMappedInstance( account, accountEntity );

        accountEntity.setAccountOwnerFullName( accountAccountOwnerFullName( account ) );
        accountEntity.setAccountOwnerPhoneNumber( accountAccountOwnerPhone( account ) );
        accountEntity.setAccountOwnerEmail( accountAccountOwnerEmail( account ) );
        accountEntity.setAccountOwnerBirthdate( accountAccountOwnerBirthdate( account ) );
        if ( account.getId() != null ) {
            accountEntity.setId( Long.parseLong( account.getId() ) );
        }
        accountEntity.setCurrency( account.getCurrency() );
        accountEntity.setBalance( account.getBalance() );
        accountEntity.setAccountNumber( account.getAccountNumber() );
        accountEntity.setCreatedAt( account.getCreatedAt() );
        accountEntity.setReceivedTransfer( transferListToTransferEntityList( account.getReceivedTransfer(), context ) );

        return accountEntity;
    }

    @Override
    public Account toModel(AccountEntity accountEntity, CycleAvoidingMappingContext context) {
        Account target = context.getMappedInstance( accountEntity, Account.class );
        if ( target != null ) {
            return target;
        }

        if ( accountEntity == null ) {
            return null;
        }

        AccountOwner accountOwner = null;
        String currency = null;
        BigDecimal balance = null;
        String accountNumber = null;

        accountOwner = accountEntityToAccountOwner( accountEntity, context );
        currency = accountEntity.getCurrency();
        balance = accountEntity.getBalance();
        accountNumber = accountEntity.getAccountNumber();

        Account account = new Account( accountOwner, accountNumber, currency, balance );

        context.storeMappedInstance( accountEntity, account );

        if ( accountEntity.getId() != null ) {
            account.setId( String.valueOf( accountEntity.getId() ) );
        }
        account.setCreatedAt( accountEntity.getCreatedAt() );
        account.setReceivedTransfer( transferEntityListToTransferList( accountEntity.getReceivedTransfer(), context ) );

        return account;
    }

    private String accountAccountOwnerFullName(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountOwner accountOwner = account.getAccountOwner();
        if ( accountOwner == null ) {
            return null;
        }
        String fullName = accountOwner.getFullName();
        if ( fullName == null ) {
            return null;
        }
        return fullName;
    }

    private String accountAccountOwnerPhone(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountOwner accountOwner = account.getAccountOwner();
        if ( accountOwner == null ) {
            return null;
        }
        String phone = accountOwner.getPhone();
        if ( phone == null ) {
            return null;
        }
        return phone;
    }

    private String accountAccountOwnerEmail(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountOwner accountOwner = account.getAccountOwner();
        if ( accountOwner == null ) {
            return null;
        }
        String email = accountOwner.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String accountAccountOwnerBirthdate(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountOwner accountOwner = account.getAccountOwner();
        if ( accountOwner == null ) {
            return null;
        }
        String birthdate = accountOwner.getBirthdate();
        if ( birthdate == null ) {
            return null;
        }
        return birthdate;
    }

    protected TransferEntity transferToTransferEntity(Transfer transfer, CycleAvoidingMappingContext context) {
        TransferEntity target = context.getMappedInstance( transfer, TransferEntity.class );
        if ( target != null ) {
            return target;
        }

        if ( transfer == null ) {
            return null;
        }

        TransferEntity transferEntity = new TransferEntity();

        context.storeMappedInstance( transfer, transferEntity );

        transferEntity.setValueDate( transfer.getValueDate() );
        transferEntity.setFromAccount( toEntity( transfer.getFromAccount(), context ) );
        transferEntity.setToAccount( toEntity( transfer.getToAccount(), context ) );
        transferEntity.setAmount( transfer.getAmount() );
        transferEntity.setCurrency( transfer.getCurrency() );
        transferEntity.setApprovedBy( transfer.getApprovedBy() );

        return transferEntity;
    }

    protected List<TransferEntity> transferListToTransferEntityList(List<Transfer> list, CycleAvoidingMappingContext context) {
        List<TransferEntity> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<TransferEntity> list1 = new ArrayList<TransferEntity>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( Transfer transfer : list ) {
            list1.add( transferToTransferEntity( transfer, context ) );
        }

        return list1;
    }

    protected AccountOwner accountEntityToAccountOwner(AccountEntity accountEntity, CycleAvoidingMappingContext context) {
        AccountOwner target = context.getMappedInstance( accountEntity, AccountOwner.class );
        if ( target != null ) {
            return target;
        }

        if ( accountEntity == null ) {
            return null;
        }

        String fullName = null;
        String phone = null;
        String email = null;
        String birthdate = null;

        fullName = accountEntity.getAccountOwnerFullName();
        phone = accountEntity.getAccountOwnerPhoneNumber();
        email = accountEntity.getAccountOwnerEmail();
        birthdate = accountEntity.getAccountOwnerBirthdate();

        AccountOwner accountOwner = new AccountOwner( fullName, email, phone, birthdate );

        context.storeMappedInstance( accountEntity, accountOwner );

        return accountOwner;
    }

    protected Transfer transferEntityToTransfer(TransferEntity transferEntity, CycleAvoidingMappingContext context) {
        Transfer target = context.getMappedInstance( transferEntity, Transfer.class );
        if ( target != null ) {
            return target;
        }

        if ( transferEntity == null ) {
            return null;
        }

        Transfer transfer = new Transfer();

        context.storeMappedInstance( transferEntity, transfer );

        transfer.setValueDate( transferEntity.getValueDate() );
        transfer.setFromAccount( toModel( transferEntity.getFromAccount(), context ) );
        transfer.setToAccount( toModel( transferEntity.getToAccount(), context ) );
        transfer.setAmount( transferEntity.getAmount() );
        transfer.setCurrency( transferEntity.getCurrency() );
        transfer.setApprovedBy( transferEntity.getApprovedBy() );

        return transfer;
    }

    protected List<Transfer> transferEntityListToTransferList(List<TransferEntity> list, CycleAvoidingMappingContext context) {
        List<Transfer> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<Transfer> list1 = new ArrayList<Transfer>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( TransferEntity transferEntity : list ) {
            list1.add( transferEntityToTransfer( transferEntity, context ) );
        }

        return list1;
    }
}
