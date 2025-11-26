package com.jpmc.midascore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private final String topic;

    // CHANGED: <String, Transaction> to <String, String>
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(@Value("${general.kafka-topic}") String topic,
                         // CHANGED: <String, Transaction> to <String, String>
                         KafkaTemplate<String, String> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String transaction) {
        System.out.println("Sending transaction: " + transaction);
        kafkaTemplate.send(topic, transaction);
    }
}