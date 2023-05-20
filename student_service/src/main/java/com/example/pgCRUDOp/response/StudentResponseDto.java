package com.example.pgCRUDOp.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StudentResponseDto {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fatherName;
    private String emailId;
    private Long mobileNumber;

}
