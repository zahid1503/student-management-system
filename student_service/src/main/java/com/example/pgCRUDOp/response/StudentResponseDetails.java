package com.example.pgCRUDOp.response;


import com.example.pgCRUDOp.models.LaptopResponseModel;
import lombok.Data;

import java.util.List;

@Data
public class StudentResponseDetails {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fatherName;
    private String emailId;
    private Long mobileNumber;
    private List<LaptopResponseModel> laptopResponseModels;

}
