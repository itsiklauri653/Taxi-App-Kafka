package com.epam.ge.taxiappkafka.services;

import com.epam.ge.taxiappkafka.models.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerService
{
    private static final Logger logger =
            LoggerFactory.getLogger(KafkaProducerService.class);

    @Value(value = "${input.topic.name}")
    private String inputTopicName;

    private final KafkaTemplate<String, Vehicle> inputKafkaTemplate;

    @Value(value = "${output.topic.name}")
    private String outputTopicName;

    private final KafkaTemplate<String, String> outputKafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Vehicle> inputKafkaTemplate, KafkaTemplate<String, String> outputKafkaTemplate) {
        this.inputKafkaTemplate = inputKafkaTemplate;
        this.outputKafkaTemplate = outputKafkaTemplate;
    }


    public void sendVehicleSignal(Vehicle vehicle) {
        ListenableFuture<SendResult<String, Vehicle>> future
                = this.inputKafkaTemplate.send(inputTopicName, vehicle);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Vehicle> result) {
                logger.info("Sent vehicle signal: " + vehicle
                        + " with offset: " + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Unable to send vehicle signal : " + vehicle, ex);
            }
        });
    }

    public void sendDistanceInfo(String message) {
        ListenableFuture<SendResult<String, String>> future
                = this.outputKafkaTemplate.send(outputTopicName, message);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Sent message: " + message +
                        " with offset: " + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Unable to send message : " + message, ex);
            }
        });
    }
}
