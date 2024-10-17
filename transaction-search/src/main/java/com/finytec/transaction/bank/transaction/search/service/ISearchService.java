package com.finytec.transaction.bank.transaction.search.service;

import com.finytec.transaction.bank.transaction.search.dto.TransactionDto;

import java.io.IOException;
import java.util.List;

public interface ISearchService {

    List<TransactionDto> getAll() throws IOException;

    TransactionDto getById(String id) throws IOException;
}
