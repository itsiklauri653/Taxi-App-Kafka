package com.epam.ge.taxiappkafka.validator;

import com.epam.ge.taxiappkafka.models.Vehicle;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class VehicleValidator {

    public boolean validateVehicleSignal(Vehicle vehicle){
        return vehicle.getId() != null && vehicle.getStartX() != null &&
                vehicle.getEndX() != null && vehicle.getStartY() != null &&
                vehicle.getEndY() != null;
    }
}
