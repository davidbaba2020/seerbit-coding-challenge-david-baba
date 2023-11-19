package com.davacom.seerbitcodingchallenge.service;

import com.davacom.seerbitcodingchallenge.entities.Transaction;
import com.davacom.seerbitcodingchallenge.entities.TransactionStatistics;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public final class TransactionService {
    private final ConcurrentHashMap<Instant, BigDecimal> transactions = new ConcurrentHashMap<>();
    private final Object lock = new Object();

    public void createTransaction(Transaction transaction) {
        Instant now = Instant.now();
        Instant transactionTime = transaction.timestamp();
        if (transactionTime.isBefore(now.minusSeconds(30))) {
            return; // Transaction is older than 30 seconds
        }

        BigDecimal roundedAmount = transaction.amount().setScale(2, RoundingMode.HALF_UP);
        transactions.put(transactionTime, roundedAmount);
        updateStatistics();
    }

    public TransactionStatistics getStatistics() {
        synchronized (lock) {
            DoubleSummaryStatistics stats = transactions.keySet().stream()
                    .filter(timestamp -> timestamp.isAfter(Instant.now().minusSeconds(30)))
                    .mapToDouble(timestamp -> transactions.get(timestamp).doubleValue())
                    .summaryStatistics();

            return new TransactionStatistics(
                    BigDecimal.valueOf(stats.getSum()).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(stats.getAverage()).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(stats.getMax()).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(stats.getMin()).setScale(2, RoundingMode.HALF_UP),
                    stats.getCount()
            );
        }
    }

    public List<Transaction> getTransactions() {
        Instant thirtySecondsAgo = Instant.now().minusSeconds(30);
        synchronized (lock) {
            return transactions.entrySet().stream()
                    .filter(entry -> entry.getKey().isAfter(thirtySecondsAgo))
                    .map(entry -> new Transaction(entry.getValue(), entry.getKey()))
                    .collect(Collectors.toList());
        }
    }

    public void deleteAllTransactions() {
        synchronized (lock) {
            transactions.clear();
        }
    }

    private void updateStatistics() {
        synchronized (lock) {
            transactions.keySet().removeIf(timestamp -> timestamp.isBefore(Instant.now().minusSeconds(30)));
        }
    }
}
