package com.finytec.transacion.bank.transaction.consumer.domain.repository;

import com.finytec.transacion.bank.transaction.consumer.domain.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
}
