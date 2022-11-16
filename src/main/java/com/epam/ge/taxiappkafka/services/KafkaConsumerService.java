package com.epam.ge.taxiappkafka.services;

import com.epam.ge.taxiappkafka.models.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final KafkaProducerService producerService;
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    public KafkaConsumerService(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @KafkaListener(topics = "${input.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "vehicleListenerContainerFactory")
    public void consume(Vehicle vehicle) {
        logger.info(String.format("Vehicle signal recieved -> %s", vehicle));
        double distance = Math.sqrt(Math.pow((vehicle.getEndX() - vehicle.getStartX()),2) +
                Math.pow((vehicle.getEndY() - vehicle.getStartY()),2));
        producerService.sendDistanceInfo("Distance: " + distance);

    }

    @KafkaListener(topics = "${output.topic.name}",
            groupId = "${output.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message) {
        logger.info(String.format("Message received: %s",message));
    }
}
