package com.example.pgCRUDOp.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;
    private String fatherName;
    private String emailId;
    private Long mobileNumber;

    // add middle name
    // user - book along author
    // add in student
}
