package com.finytec.transacion.bank.transaction.consumer.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Bean(destroyMethod = "close")
    public RestHighLevelClient createClient() {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "B0njxm+JGLS6atTu0B+C"));

        return new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
                        .setHttpClientConfigCallback((config) ->
                                config.setDefaultCredentialsProvider(credentialsProvider))
        );
    }
}
