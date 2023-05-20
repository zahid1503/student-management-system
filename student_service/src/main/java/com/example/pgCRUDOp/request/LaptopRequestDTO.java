package com.example.pgCRUDOp.request;

import lombok.Data;

@Data
public class LaptopRequestDTO {

    private Long id;

    private String brand;

    private String model;

    private String serialNumber;

    private Long studentId;
}
