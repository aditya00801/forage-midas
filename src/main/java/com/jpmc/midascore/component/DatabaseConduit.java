package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DatabaseConduit {
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;
    private final RestTemplate restTemplate;

    public DatabaseConduit(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
        this.restTemplate = restTemplate;
    }

    public void save(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if (sender != null && recipient != null) {
            if (sender.getBalance() >= transaction.getAmount()) {
                try {
                    // 1. Call Incentive API
                    Incentive incentive = restTemplate.postForObject("http://localhost:8080/incentive", transaction, Incentive.class);
                    float incentiveAmount = incentive.getAmount();

                    // 2. Update Balances
                    sender.setBalance(sender.getBalance() - transaction.getAmount());
                    recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

                    // 3. Save to Database
                    userRepository.save(sender);
                    userRepository.save(recipient);

                    TransactionRecord record = new TransactionRecord(sender, recipient, transaction.getAmount(), incentiveAmount);
                    transactionRecordRepository.save(record);

                } catch (Exception e) {
                    // Log error if API is down, but don't crash the app
                    System.out.println("Error calling incentive API: " + e.getMessage());
                }
            }
        }
    }
}