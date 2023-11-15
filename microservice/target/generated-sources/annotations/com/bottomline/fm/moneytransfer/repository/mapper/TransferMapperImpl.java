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
public class TransferMapperImpl implements TransferMapper {

    @Override
    public TransferEntity toEntity(Transfer transfer, CycleAvoidingMappingContext context) {
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
        transferEntity.setFromAccount( accountToAccountEntity( transfer.getFromAccount(), context ) );
        transferEntity.setToAccount( accountToAccountEntity( transfer.getToAccount(), context ) );
        transferEntity.setAmount( transfer.getAmount() );
        transferEntity.setCurrency( transfer.getCurrency() );
        transferEntity.setApprovedBy( transfer.getApprovedBy() );

        return transferEntity;
    }

    @Override
    public Transfer toModel(TransferEntity transferEntity, CycleAvoidingMappingContext context) {
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
        transfer.setFromAccount( accountEntityToAccount( transferEntity.getFromAccount(), context ) );
        transfer.setToAccount( accountEntityToAccount( transferEntity.getToAccount(), context ) );
        transfer.setAmount( transferEntity.getAmount() );
        transfer.setCurrency( transferEntity.getCurrency() );
        transfer.setApprovedBy( transferEntity.getApprovedBy() );

        return transfer;
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
            list1.add( toEntity( transfer, context ) );
        }

        return list1;
    }

    protected AccountEntity accountToAccountEntity(Account account, CycleAvoidingMappingContext context) {
        AccountEntity target = context.getMappedInstance( account, AccountEntity.class );
        if ( target != null ) {
            return target;
        }

        if ( account == null ) {
            return null;
        }

        AccountEntity accountEntity = new AccountEntity();

        context.storeMappedInstance( account, accountEntity );

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
            list1.add( toModel( transferEntity, context ) );
        }

        return list1;
    }

    protected Account accountEntityToAccount(AccountEntity accountEntity, CycleAvoidingMappingContext context) {
        Account target = context.getMappedInstance( accountEntity, Account.class );
        if ( target != null ) {
            return target;
        }

        if ( accountEntity == null ) {
            return null;
        }

        String currency = null;
        BigDecimal balance = null;
        String accountNumber = null;

        currency = accountEntity.getCurrency();
        balance = accountEntity.getBalance();
        accountNumber = accountEntity.getAccountNumber();

        AccountOwner accountOwner = null;

        Account account = new Account( accountOwner, accountNumber, currency, balance );

        context.storeMappedInstance( accountEntity, account );

        if ( accountEntity.getId() != null ) {
            account.setId( String.valueOf( accountEntity.getId() ) );
        }
        account.setCreatedAt( accountEntity.getCreatedAt() );
        account.setReceivedTransfer( transferEntityListToTransferList( accountEntity.getReceivedTransfer(), context ) );

        return account;
    }
}
