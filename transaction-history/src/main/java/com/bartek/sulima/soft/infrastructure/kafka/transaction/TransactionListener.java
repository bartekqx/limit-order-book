package com.bartek.sulima.soft.infrastructure.kafka.transaction;

import com.bartek.sulima.soft.domain.TransactionService;
import com.bartek.sulima.soft.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class TransactionListener {

    private final TransactionService transactionService;

    @StreamListener(TransactionStream.IN_CHANNEL)
    public void handleMessage(@Payload Transaction transaction) {
        log.info("Transaction message consumed: " + transaction);
        transactionService.save(transaction);
    }
}
