package com.finytec.transacion.bank.transaction.consumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finytec.transacion.bank.transaction.consumer.domain.TransactionEntity;
import com.finytec.transacion.bank.transaction.consumer.domain.service.TransactionServiceDb;
import com.finytec.transacion.bank.transaction.consumer.model.Transaction;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    @Autowired
    TransactionServiceDb service;
    @Autowired
    private RestHighLevelClient client;

    @Autowired(required = true)
    private ObjectMapper mapper;

    @KafkaListener(topics = "bank-transactions", groupId = "bankGroup")
    public void consume(List<Transaction> transactions, Acknowledgment acknowledgment) throws JsonProcessingException {
        for (Transaction transaction : transactions) {
            log.info("Input bank Transaction : {}", transaction);
            IndexRequest indexRequest = buildIndexRequest(transaction.getId(), mapper.writeValueAsString(transaction));
            indexRequest.timeout(TimeValue.timeValueMinutes(3));  // Aumentar el timeout si es necesario

            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setAmount(transaction.getAmount());
            transactionEntity.setDestinationAccount(transaction.getDestinationAccount());
            transactionEntity.setType(transaction.getType());
            transactionEntity.setSourceAccount(transaction.getSourceAccount());
            transactionEntity.setTimestamp(transaction.getTimestamp());

            TransactionEntity dataSaved = service.saveTransaction(transactionEntity);

            log.info("saved bank Transaction  : {}", mapper.writeValueAsString(dataSaved));
            client.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
                @Override
                public void onResponse(IndexResponse indexResponse) {
                    log.info("Documento indexado con éxito en ElasticSearch: {}", indexResponse.getId());
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("Error al indexar en ElasticSearch: {}", e.getMessage());
                }
            });

        }
        // Confirmar el desplazamiento manualmente después de procesar el lote
        acknowledgment.acknowledge();
    }
    private IndexRequest buildIndexRequest(String key, String value) {
        IndexRequest request = new IndexRequest("bank-transactions");

        request.id(key); // El id del documento en ElasticSearch
        request.source(value, XContentType.JSON); // El valor en formato JSON
        return request;
    }
}