package com.example.pgCRUDOp.services.impl;

import com.example.pgCRUDOp.entities.Student;
import com.example.pgCRUDOp.exceptions.ErrorCode;
import com.example.pgCRUDOp.exceptions.StudentNotFoundException;
import com.example.pgCRUDOp.repositories.IStudentRepository;
import com.example.pgCRUDOp.request.StudentRequestDto;
import com.example.pgCRUDOp.response.StudentResponseDto;
import com.example.pgCRUDOp.services.IStudentService;
import com.example.pgCRUDOp.utility.Constants;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentService implements IStudentService {

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StudentResponseDto createStudent(StudentRequestDto requestDto) {

        log.debug("creating student starts");
        try{
            //must provide first name and mobile number and mail-id of student
            if (requestDto.getFirstName() == null || requestDto.getFirstName().isEmpty() ) {
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND, Constants.STUDENT_NAME_NOT_FOUND);
            }
            if (requestDto.getMobileNumber() == null) {
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND, Constants.PHONE_NOT_FOUND);
            }
            if (requestDto.getEmailId() == null || requestDto.getEmailId().isEmpty()) {
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND, Constants.EMAIL_ID_NOT_FOUND);
            }
            Student student = studentRepository.findStudentByMailIdAndMobileNumber(requestDto.getMobileNumber(),requestDto.getEmailId());

            // mail id and mobile number are should be unique
            if(!Objects.isNull(student)){
                throw new StudentNotFoundException(ErrorCode.CONFLICT,Constants.STUDENT_ALREADY_EXIST);
            }


            Student existingStudent = modelMapper.map(requestDto, Student.class);
            Student savedEntity = studentRepository.save(existingStudent);
            StudentResponseDto response = modelMapper.map(savedEntity,StudentResponseDto.class);

            log.debug("creating student ends");
            return response;
        }catch (Exception e){
            log.error("student not found");
            if(Constants.STUDENT_NAME_NOT_FOUND.equals(e.getMessage())){
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND,Constants.STUDENT_NAME_NOT_FOUND);
            }else if(Constants.PHONE_NOT_FOUND.equals(e.getMessage())){
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND, Constants.PHONE_NOT_FOUND);
            }else if(Constants.EMAIL_ID_NOT_FOUND.equals(e.getMessage())){
                throw new StudentNotFoundException(ErrorCode.BAD_REQUEST,Constants.EMAIL_ID_NOT_FOUND);
            }else if(Constants.STUDENT_ALREADY_EXIST.equals(e.getMessage())){
                throw new StudentNotFoundException(ErrorCode.BAD_REQUEST,Constants.STUDENT_ALREADY_EXIST);
            }
            throw new StudentNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.STUDENT_ADD_FAIL);
        }
    }


    @Override
    public StudentResponseDto updateStudent(StudentRequestDto request) {
        log.debug("updating student starts");

        try {

            if(request.getId()== null){
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND, Constants.STUDENT_ID_NOT_FOUND);
            }

            Optional<Student> existingStudent = studentRepository.findById(request.getId());

            if(existingStudent.isEmpty()){
                throw new StudentNotFoundException(ErrorCode.BAD_REQUEST,Constants.STUDENT_NOT_FOUND);
            }
            Student student = studentRepository.findStudentByMailIdAndMobileNumber(request.getMobileNumber(),request.getEmailId());

            if(Objects.isNull(student)){
                throw new StudentNotFoundException(ErrorCode.CONFLICT,Constants.STUDENT_ALREADY_EXIST);
            }

            Student studentEntity= modelMapper.map(request, Student.class);
            Student savedEntity = studentRepository.save(studentEntity);
            StudentResponseDto response = modelMapper.map(savedEntity,StudentResponseDto.class);
            log.debug("updating student ends");
            return response;
        }catch (Exception e){
            log.error("student details not found");
            if(Constants.STUDENT_NOT_FOUND.equals(e.getMessage())){
                throw new StudentNotFoundException(ErrorCode.CONFLICT,Constants.STUDENT_NOT_FOUND);
            }else if(Constants.STUDENT_ID_NOT_FOUND.equals(e.getMessage())){
                throw new StudentNotFoundException(ErrorCode.BAD_REQUEST,Constants.STUDENT_ID_NOT_FOUND);
            }else if(Constants.STUDENT_ALREADY_EXIST.equals(e.getMessage())){
                throw new StudentNotFoundException(ErrorCode.BAD_REQUEST,Constants.STUDENT_ALREADY_EXIST);
            }
            throw new StudentNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.STUDENT_UPDATE_FAIL);
        }
    }


    @Override
    public List<StudentResponseDto> findStudentByDetails(String firstName, String lastName, Long mobileNumber) {
        log.debug("fetching the user details");

        try {
            List<Student> students = studentRepository.findStudentByDetails(firstName, lastName, mobileNumber);


            if (students.isEmpty()) {
                throw new StudentNotFoundException(ErrorCode.NO_CONTENT, Constants.EMPTY_STUDENT_LIST); // empty response
            }
            List<StudentResponseDto> response = students.stream()
                    .map(student -> modelMapper.map(student, StudentResponseDto.class)).collect(Collectors.toList());

            return response;

        } catch (Exception e) {
            if (Constants.EMPTY_STUDENT_LIST.equals(e.getMessage())) {
                throw new StudentNotFoundException(ErrorCode.NO_CONTENT, Constants.EMPTY_STUDENT_LIST);
            }
            throw new StudentNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR,Constants.STUDENT_DETAILS_FETCH_FAIL);
        }
    }



    @Override
    public StudentResponseDto findStudentDetails(String firstName) {
        log.info("fetching student data starts");
        try{
            Student student = studentRepository.findStudentDetailsByFirstName(firstName);
            if (Objects.isNull(student)) {
                throw new StudentNotFoundException(ErrorCode.NO_CONTENT, Constants.STUDENT_NOT_FOUND);
            }
            StudentResponseDto response = new StudentResponseDto();

            response.setId(student.getId());
            response.setFirstName(firstName);
            response.setLastName(student.getLastName());
            response.setMiddleName(student.getMiddleName());
            log.info("fetching student data ends");
            return response;
        }catch (Exception e){
            log.error("student not found");
            throw new StudentNotFoundException(ErrorCode.NOT_FOUND,Constants.STUDENT_NOT_FOUND);
        }
    }

}
