package com.bottomline.fm.moneytransfer.repository.base;

import com.bottomline.fm.moneytransfer.model.Transfer;
import com.bottomline.fm.moneytransfer.repository.entity.TransferEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

    List<Transfer> findAllByOrderByValueDateDesc(Pageable pageable);
}
