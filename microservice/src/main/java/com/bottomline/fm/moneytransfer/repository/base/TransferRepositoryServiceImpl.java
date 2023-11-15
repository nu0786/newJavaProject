package com.bottomline.fm.moneytransfer.repository.base;

import com.bottomline.fm.moneytransfer.model.Transfer;
import com.bottomline.fm.moneytransfer.repository.entity.TransferEntity;
import com.bottomline.fm.moneytransfer.repository.mapper.CycleAvoidingMappingContext;
import com.bottomline.fm.moneytransfer.repository.mapper.TransferMapper;
import com.bottomline.fm.moneytransfer.repository.spi.TransferRepositoryService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("transferRepositoryService")
public class TransferRepositoryServiceImpl implements TransferRepositoryService {
    protected final TransferRepository transferRepository;
    protected final TransferMapper transferMapper = TransferMapper.INSTANCE;

    public TransferRepositoryServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public Transfer create(Transfer transfer) {
        return transferMapper.toModel(transferRepository.save(transferMapper.toEntity(transfer, new CycleAvoidingMappingContext())), new CycleAvoidingMappingContext());
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return transferRepository.findById(id).map(t -> transferMapper.toModel(t, new CycleAvoidingMappingContext()));
    }

    /**
     *
     * @return list of account numbers ordered by number of transfer they made
     */
    @Override
    public List<String> topSenderAccounts() {
        List<String> topSendersAccounts = new ArrayList<>();
        List<TransferEntity> latestTransfer = transferRepository.findAll();
        Map<String, Long> groupedAccounts = latestTransfer.stream().collect(Collectors.groupingBy(transfer -> transfer.getFromAccount().getAccountNumber(), Collectors.counting()));

        main_loop: for(Map.Entry<String, Long> entry : groupedAccounts.entrySet()) {
            for(String accountNumber : topSendersAccounts) {
                if (entry.getValue() >= groupedAccounts.get(accountNumber)) {
                    topSendersAccounts.add(topSendersAccounts.indexOf(accountNumber), entry.getKey());
                    continue main_loop;
                }
            }
            topSendersAccounts.add(entry.getKey());
        }
        return topSendersAccounts;
    }
}
