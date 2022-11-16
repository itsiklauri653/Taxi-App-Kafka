package com.epam.ge.taxiappkafka.controllers;

import com.epam.ge.taxiappkafka.models.Vehicle;
import com.epam.ge.taxiappkafka.services.KafkaProducerService;
import com.epam.ge.taxiappkafka.validator.VehicleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/taxi")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducerService producerService;
    private final VehicleValidator validator;

    @PostMapping("/sendSignal")
    public ResponseEntity<?> sendSignal(@RequestBody List<Vehicle> vehicles) {
        long validVehiclesCount = vehicles.stream().filter(validator::validateVehicleSignal)
                .count();
        if(validVehiclesCount == vehicles.size()){
            vehicles.forEach(producerService::sendVehicleSignal);
            return ResponseEntity.ok("Signals Sent Successfully!");
        }
        return ResponseEntity.badRequest().body("Request data is not valid!!!");
    }
}
