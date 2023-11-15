package com.bottomline.fm.moneytransfer.service.spi;

public interface AccountNumberGenerator {
    Integer generate();
    Integer lastSequence();
}
