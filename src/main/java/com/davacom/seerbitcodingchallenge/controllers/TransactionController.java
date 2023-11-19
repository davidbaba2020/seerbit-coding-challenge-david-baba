package com.davacom.seerbitcodingchallenge.controllers;

import com.davacom.seerbitcodingchallenge.entities.Transaction;
import com.davacom.seerbitcodingchallenge.entities.TransactionStatistics;
import com.davacom.seerbitcodingchallenge.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<TransactionStatistics> getTransactionStatistics() {
        return ResponseEntity.ok(transactionService.getStatistics());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAllTransactions() {
        transactionService.deleteAllTransactions();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getTransactions();
        return ResponseEntity.ok(transactions);
    }
}
