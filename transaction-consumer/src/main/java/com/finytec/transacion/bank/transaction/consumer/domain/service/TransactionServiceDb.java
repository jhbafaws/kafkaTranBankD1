package com.finytec.transacion.bank.transaction.consumer.domain.service;

import com.finytec.transacion.bank.transaction.consumer.domain.TransactionEntity;
import com.finytec.transacion.bank.transaction.consumer.domain.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceDb {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionEntity saveTransaction(TransactionEntity transaction) {
        return transactionRepository.save(transaction);
    }
}
