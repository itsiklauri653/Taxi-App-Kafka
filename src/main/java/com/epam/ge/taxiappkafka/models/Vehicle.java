package com.epam.ge.taxiappkafka.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private Long id;
    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;
}
