package com.example.pgCRUDOp.services;


import com.example.pgCRUDOp.request.LaptopRequestDTO;
import com.example.pgCRUDOp.response.LaptopResponseDTO;
import com.example.pgCRUDOp.response.StudentResponseDetails;

public interface ILaptopService {

    LaptopResponseDTO addLaptop(LaptopRequestDTO request);

    LaptopResponseDTO updateLaptop(LaptopRequestDTO request);

    StudentResponseDetails findStudentWithLaptops(String firstName);
}
