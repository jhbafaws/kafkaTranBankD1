package com.finytec.transaction.bank.transaction.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finytec.transaction.bank.transaction.producer.model.Transaction;
import com.finytec.transaction.bank.transaction.producer.service.ITransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/bank")
public class TransactionController {

    private final static Logger log = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    ITransactionService iTransactionService;

    @Autowired(required = true)
    private ObjectMapper mapper;
    @GetMapping
    public ResponseEntity<Transaction> showTransaction(@RequestBody Transaction transaction) throws JsonProcessingException {

        log.info("Enter Controller : {}", mapper.writeValueAsString(transaction));

        iTransactionService.sendKafkaUser(transaction);

        return ResponseEntity.ok(transaction);


    }
}
