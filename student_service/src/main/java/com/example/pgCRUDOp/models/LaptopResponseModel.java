package com.example.pgCRUDOp.models;

import lombok.Data;

@Data
public class LaptopResponseModel {

    private Long id ;

    private String brand;

    private String model;

    private String serialNumber;

    private Long studentId;
}
