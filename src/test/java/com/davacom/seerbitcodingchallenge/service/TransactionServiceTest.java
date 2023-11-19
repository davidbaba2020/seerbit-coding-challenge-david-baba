package com.davacom.seerbitcodingchallenge.service;

import com.davacom.seerbitcodingchallenge.entities.Transaction;
import com.davacom.seerbitcodingchallenge.entities.TransactionStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    public void testCreateTransactionWithin30Seconds() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(100.55), Instant.now());
        transactionService.createTransaction(transaction);

        List<Transaction> transactions = transactionService.getTransactions();
        assertEquals(1, transactions.size());
        assertTrue(transactions.contains(transaction));
    }

    @Test
    public void testCreateTransactionOlderThan30Seconds() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(50.25), Instant.now().minusSeconds(40));
        transactionService.createTransaction(transaction);

        List<Transaction> transactions = transactionService.getTransactions();
        assertEquals(0, transactions.size());
        assertFalse(transactions.contains(transaction));
    }

    @Test
    public void testGetStatistics() {
        // Assuming some transactions exist
        Transaction transaction1 = new Transaction(BigDecimal.valueOf(30.50), Instant.now().minusSeconds(20));
        Transaction transaction2 = new Transaction(BigDecimal.valueOf(70.25), Instant.now().minusSeconds(15));
        transactionService.createTransaction(transaction1);
        transactionService.createTransaction(transaction2);

        TransactionStatistics statistics = transactionService.getStatistics();

        assertEquals(BigDecimal.valueOf(100.75).setScale(2), statistics.sum());
        assertEquals(BigDecimal.valueOf(50.38).setScale(2), statistics.avg());
        assertEquals(BigDecimal.valueOf(70.25).setScale(2), statistics.max());
        assertEquals(BigDecimal.valueOf(30.50).setScale(2), statistics.min());
        assertEquals(2, statistics.count());
    }

    @Test
    public void testDeleteAllTransactions() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(40.20), Instant.now().minusSeconds(25));
        transactionService.createTransaction(transaction);

        transactionService.deleteAllTransactions();

        List<Transaction> transactions = transactionService.getTransactions();
        assertEquals(0, transactions.size());
        assertFalse(transactions.contains(transaction));
    }
}