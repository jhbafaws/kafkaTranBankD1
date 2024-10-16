package com.finytec.transaction.bank.transaction.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finytec.transaction.bank.transaction.producer.model.Transaction;

public interface ITransactionService {
    void sendKafkaUser(Transaction transaction) throws JsonProcessingException;

}
