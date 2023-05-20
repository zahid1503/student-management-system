package com.example.pgCRUDOp.services;



import com.example.pgCRUDOp.request.StudentRequestDto;
import com.example.pgCRUDOp.response.StudentResponseDto;

import java.util.List;

public interface IStudentService {

    StudentResponseDto createStudent(StudentRequestDto requestDto) ;

    StudentResponseDto updateStudent(StudentRequestDto request) ;

    List<StudentResponseDto> findStudentByDetails(String firstName, String lastName, Long mobileNumber);

    StudentResponseDto findStudentDetails(String firstName);
}
