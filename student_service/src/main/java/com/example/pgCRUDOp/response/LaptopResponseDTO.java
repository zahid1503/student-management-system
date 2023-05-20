package com.example.pgCRUDOp.response;

import lombok.Data;

@Data
public class LaptopResponseDTO {

    private Long id;

    private String brand;

    private String model;

    private String serialNumber;

    private Long studentId;
}
