package com.bottomline.fm.moneytransfer.controller;

import com.bottomline.fm.moneytransfer.dto.AccountCreationRequest;
import com.bottomline.fm.moneytransfer.model.Account;
import com.bottomline.fm.moneytransfer.model.AccountOwner;
import com.bottomline.fm.moneytransfer.service.spi.AccountService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    private final AccountService accountService;

    public AccountController(@Qualifier("accountService") AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Account create(@RequestBody AccountCreationRequest accountCreationRequest) {
        return accountService.createAccount(
            new AccountOwner(accountCreationRequest.getFullName(),
                accountCreationRequest.getEmail(),
                accountCreationRequest.getPhone(),
                accountCreationRequest.getBirthdate()),
            accountCreationRequest.getCurrency());
    }

    @GetMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Account get(@PathVariable("accountNumber") String accountNumber) {
        return accountService.findAccountByNumber(accountNumber);
    }
}
