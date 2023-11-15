package com.bottomline.fm.moneytransfer.service.base;

import com.bottomline.fm.moneytransfer.service.spi.AccountNumberGenerator;
import org.springframework.stereotype.Service;

@Service("accountNumberGenerator")
public class AccountNumberGeneratorImpl implements AccountNumberGenerator {
    private Integer sequence;

    public AccountNumberGeneratorImpl() {
        this.sequence = 0;
    }

    public Integer generate() {
        return this.sequence++;
    }

    public Integer lastSequence() {
        return sequence;
    }
}
