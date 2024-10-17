package com.finytec.transaction.bank.transaction.search.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finytec.transaction.bank.transaction.search.dto.TransactionDto;
import com.finytec.transaction.bank.transaction.search.service.ISearchService;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService implements ISearchService {

    @Autowired
    private RestHighLevelClient client;

    @Autowired(required = true)
    private ObjectMapper mapper;

    @Override
    public TransactionDto getById(String id) throws IOException {
        GetRequest getRequest = new GetRequest("bank-transactions", id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

        if (getResponse.isExists()) {
            return mapper.readValue(getResponse.getSourceAsString(), TransactionDto.class);
        } else {
            return null;
        }
    }

    @Override
    public List<TransactionDto> getAll() throws IOException {

        SearchRequest searchRequest = new SearchRequest("bank-transactions");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        sourceBuilder.size(1000);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        List<TransactionDto> transactions = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            transactions.add(mapper.readValue(hit.getSourceAsString(), TransactionDto.class));
        }

        return transactions;
    }


}
