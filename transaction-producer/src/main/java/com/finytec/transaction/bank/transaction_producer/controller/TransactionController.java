package com.finytec.transaction.bank.transaction_producer.controller;

import com.finytec.transaction.bank.transaction_producer.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/bank")
public class TransactionController {

    @GetMapping
    public ResponseEntity<Transaction> showTransaction(@RequestBody Transaction transaction){

        return ResponseEntity.ok(transaction);


    }
}
