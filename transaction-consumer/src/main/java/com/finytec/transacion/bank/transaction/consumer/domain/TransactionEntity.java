package com.finytec.transacion.bank.transaction.consumer.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "transactions")

public class TransactionEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private UUID id;
    @Column(name = "source_account")
    private String sourceAccount;

    @Column(name = "destination_account")
    private String destinationAccount;

    @Column(name = "amount")
    private double amount;

    @Column(name = "type")
    private String type;

    @Column(name = "timestamp")
    private String timestamp;

    public TransactionEntity() {
    }

// Getters y setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}