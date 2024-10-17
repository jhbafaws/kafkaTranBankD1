package com.finytec.transaction.bank.transaction.search.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finytec.transaction.bank.transaction.search.dto.TransactionDto;
import com.finytec.transaction.bank.transaction.search.service.ISearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/search")
public class SearchController {

    private final static Logger log = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    ISearchService service;

    @Autowired(required = true)
    private ObjectMapper mapper;

    @GetMapping
    public ResponseEntity<TransactionDto> getTransaction(@RequestParam String id) throws IOException {

        log.info("Get Transaction : {}", id);

        TransactionDto transactionDto = service.getById(id);
        return ResponseEntity.ok(transactionDto);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<TransactionDto>> getTransactions() throws IOException {


        List<TransactionDto> transactionDtoList = service.getAll();
        log.info("Get All Transaction : {}", mapper.writeValueAsString(transactionDtoList));
        return ResponseEntity.ok(transactionDtoList);
    }


}
