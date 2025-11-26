package com.jpmc.midascore.component;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseKafkaListener {

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas")
    public void listen(String transactionMessage) {
        System.out.println("RAW MESSAGE RECEIVED: " + transactionMessage);
        // The message comes in as text, e.g.: "1001, 2002, 50.0"
        // We split the string to get the numbers
        String[] parts = transactionMessage.split(", ");

        // The Amount is the 3rd item (index 2)
        System.out.println("Received Transaction Amount: " + parts[2]);
    }
}