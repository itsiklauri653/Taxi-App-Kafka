package com.epam.ge.taxiappkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    @Value(value = "${input.topic.name}")
    private String inputTopic;

    @Value(value = "${output.topic.name}")
    private String outputTopic;

    @Bean
    public NewTopic inputTopic() {
        return TopicBuilder.name(inputTopic)
                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic outputTopic() {
        return TopicBuilder.name(outputTopic)
                .partitions(3)
                .build();
    }
}
