package com.bottomline.fm.moneytransfer.service.spi;

import com.bottomline.fm.moneytransfer.exception.BadRequestException;
import com.bottomline.fm.moneytransfer.exception.NotFoundException;
import com.bottomline.fm.moneytransfer.model.Transfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransferService {
    /**
     * Do transfer money from one account, to another, without requiring any approval.
     * Business requirements were:
     * - Both accounts should be in the same currency
     * - From account should not have a negative balance at the end of the transfer
     * - Amount should be positive
     * @param fromAccountNumber
     * @param toAccountNumber
     * @param currency
     * @param amount
     * @return true when money was actually transferred, false otherwise
     */
    Optional<Transfer> performTransferWithoutApproval(String fromAccountNumber, String toAccountNumber, String currency, BigDecimal amount) throws NotFoundException, BadRequestException;

    /**
     * Gives number of transfers made by the given account in this instance of the application since last restart.
     * @param fromAccountNumber
     * @return number of transfers made by the given account
     */
    Integer numberOfTransfersForAccount(String fromAccountNumber);

    Optional<Transfer> findById(Long id);

    /**
     *
     * @return list of account numbers ordered by number of transfer they made as date of today
     */
    List<String> topSenderAccounts();
}
