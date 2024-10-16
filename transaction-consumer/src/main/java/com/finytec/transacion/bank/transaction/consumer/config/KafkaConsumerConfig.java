package com.finytec.transacion.bank.transaction.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finytec.transacion.bank.transaction.consumer.model.Transaction;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public Map<String, Object> consumerProps() {

        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "bankGroup");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.finytec.transacion.bank.transaction.consumer.model.Transaction"); // Ruta completa de la clase UserDto
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Permitir que cualquier paquete sea deserializado
        props.put("missing-topics-fatal", false);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);


        return props;

    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    public ConsumerFactory<String, Transaction> consumerFactory() {
        // Configuramos el ConsumerFactory para deserializar UserDto
        return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(), new JsonDeserializer<>(Transaction.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Transaction> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Transaction> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(50);
        factory.setBatchListener(false); // Cambiar a false para procesamiento individual
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        return factory;
    }


}
