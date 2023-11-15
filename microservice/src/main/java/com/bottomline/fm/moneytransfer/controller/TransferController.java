package com.bottomline.fm.moneytransfer.controller;

import com.bottomline.fm.moneytransfer.exception.NotFoundException;
import com.bottomline.fm.moneytransfer.model.Transfer;
import com.bottomline.fm.moneytransfer.service.spi.TransferService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Transfer find(@Param("id") Long id) {
        return transferService.findById(id).orElseThrow(() -> new NotFoundException("Cannot find transfer with id " + id));
    }
}
