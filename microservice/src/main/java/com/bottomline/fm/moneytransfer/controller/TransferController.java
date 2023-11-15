package com.bottomline.fm.moneytransfer.controller;

import com.bottomline.fm.moneytransfer.exception.NotFoundException;
import com.bottomline.fm.moneytransfer.model.Transfer;
import com.bottomline.fm.moneytransfer.service.spi.TransferService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity<List<Transfer>> getAllTransfers(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "25") int size) {
        List<Transfer> transfers = transferService.getAllTransfers(page, size);
        return ResponseEntity.ok(transfers);
    }
}
