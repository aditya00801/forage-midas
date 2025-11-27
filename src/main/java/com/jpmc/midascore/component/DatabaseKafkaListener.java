package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseKafkaListener {
    private final DatabaseConduit databaseConduit;

    public DatabaseKafkaListener(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas")
    public void listen(String transactionMessage) {
        String[] parts = transactionMessage.split(", ");
        long senderId = Long.parseLong(parts[0]);
        long recipientId = Long.parseLong(parts[1]);
        float amount = Float.parseFloat(parts[2]);

        Transaction transaction = new Transaction(senderId, recipientId, amount);
        databaseConduit.save(transaction);
    }
}