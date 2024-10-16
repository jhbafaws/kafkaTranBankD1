package com.finytec.transaction.bank.transaction.producer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finytec.transaction.bank.transaction.producer.model.Transaction;
import com.finytec.transaction.bank.transaction.producer.service.ITransactionService;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class TransactionService implements ITransactionService {

    private final static Logger log = LoggerFactory.getLogger(TransactionService.class);
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired(required = true)
    private ObjectMapper mapper;

    @Override
    @Scheduled(fixedRate = 15000)
    public void sendKafkaUser(Transaction transaction) throws JsonProcessingException {

        log.info("Enter a sendKafkaUser : {}", mapper.writeValueAsString(transaction));
        Faker faker = new Faker();

        for (int i = 0; i < 100; i++) {
            Transaction transaction1 = new Transaction();
            transaction1.setAmount(faker.number().randomDouble(3, 0, 20000));
            transaction1.setType(faker.name().name());
            Date fakeDate = new Date();
            Timestamp fakeTimestamp = new Timestamp(fakeDate.getTime());
            transaction1.setTimestamp(fakeTimestamp.toString());
            transaction1.setSourceAccount(String.valueOf(faker.number().numberBetween(1000000,2000000)));
            transaction1.setDestinationAccount(String.valueOf(faker.number().numberBetween(1000000,2000000)));
            transaction1.setId(faker.name().name());

            log.info("Transaction: {}", mapper.writeValueAsString(transaction1));
            kafkaTemplate.send("bank-transactions", transaction.getSourceAccount(), mapper.writeValueAsString(transaction1));
        }
    }
}
