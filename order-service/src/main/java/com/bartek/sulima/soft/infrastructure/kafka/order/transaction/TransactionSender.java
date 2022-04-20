package com.bartek.sulima.soft.infrastructure.kafka.order.transaction;

import com.bartek.sulima.soft.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionSender {

    private final TransactionStream stream;

    public void send(Transaction transaction) {
        stream.publisher()
                .send(MessageBuilder.withPayload(transaction)
                        .build());
        log.info("Transaction message published: " + transaction);
    }
}
