package com.example.pgCRUDOp.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "laptop")
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private String brand;

    private String model;

    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name ="student_id")
    private Student student;
}
