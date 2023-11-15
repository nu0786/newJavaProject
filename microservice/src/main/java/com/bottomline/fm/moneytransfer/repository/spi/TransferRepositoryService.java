package com.bottomline.fm.moneytransfer.repository.spi;

import com.bottomline.fm.moneytransfer.model.Transfer;

import java.util.List;
import java.util.Optional;

public interface TransferRepositoryService {

    Transfer create(Transfer transfer);

    Optional<Transfer> findById(Long id);

    List<String> topSenderAccounts();
}
