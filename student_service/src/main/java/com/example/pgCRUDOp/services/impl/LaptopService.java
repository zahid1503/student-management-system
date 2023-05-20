package com.example.pgCRUDOp.services.impl;

import com.example.pgCRUDOp.entities.Laptop;
import com.example.pgCRUDOp.entities.Student;
import com.example.pgCRUDOp.exceptions.ErrorCode;
import com.example.pgCRUDOp.exceptions.LaptopNotFoundException;
import com.example.pgCRUDOp.exceptions.StudentNotFoundException;
import com.example.pgCRUDOp.models.LaptopResponseModel;
import com.example.pgCRUDOp.repositories.ILaptopRepository;
import com.example.pgCRUDOp.repositories.IStudentRepository;
import com.example.pgCRUDOp.request.LaptopRequestDTO;
import com.example.pgCRUDOp.response.LaptopResponseDTO;
import com.example.pgCRUDOp.response.StudentResponseDetails;
import com.example.pgCRUDOp.services.ILaptopService;
import com.example.pgCRUDOp.utility.Constants;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LaptopService implements ILaptopService {

    @Autowired
    private ILaptopRepository laptopRepository;

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LaptopResponseDTO addLaptop(LaptopRequestDTO request) {
        log.debug("Adding student starts");

        try {

            if(request.getStudentId()== null){
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND, Constants.STUDENT_ID_NOT_FOUND);
            }
            // must provide brand and serial number details of laptop

            if(request.getBrand() == null || request.getBrand().isEmpty()){
                throw new LaptopNotFoundException(ErrorCode.BAD_REQUEST, Constants.BRAND_NAME_NOT_FOUND);
            }
            if(request.getSerialNumber() == null || request.getSerialNumber().isEmpty()){
                throw new LaptopNotFoundException(ErrorCode.BAD_REQUEST,Constants.SERIAL_NUMBER_NOT_FOUND);
            }

            Optional<Student> student = studentRepository.findById(request.getStudentId());
            // 1
            if(student.isEmpty()){
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND, Constants.STUDENT_NOT_FOUND);
            }

            if(laptopRepository.existsBySerialNumber(request.getSerialNumber())){
                throw new LaptopNotFoundException(ErrorCode.BAD_REQUEST, Constants.LAPTOP_IS_ALREADY_EXIST);
            }

            Laptop laptop = modelMapper.map(request,Laptop.class);
            Laptop savedAddress = laptopRepository.save(laptop);
            LaptopResponseDTO response = modelMapper.map(savedAddress,LaptopResponseDTO.class);

            log.debug("Adding student ends");
            return response;
        }catch (Exception e) {
            log.error("laptop details not found");
            if(Constants.STUDENT_NOT_FOUND.equals(e.getMessage())) {
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND, Constants.STUDENT_NOT_FOUND);
            }else if(Constants.STUDENT_ID_NOT_FOUND.equals(e.getMessage())){
                throw new LaptopNotFoundException(ErrorCode.BAD_REQUEST ,Constants.STUDENT_ID_NOT_FOUND);
            }else if(Constants.BRAND_NAME_NOT_FOUND.equals(e.getMessage())){
                throw new LaptopNotFoundException(ErrorCode.BAD_REQUEST ,Constants.BRAND_NAME_NOT_FOUND);
            }else if(Constants.SERIAL_NUMBER_NOT_FOUND.equals(e.getMessage())){
                throw new LaptopNotFoundException(ErrorCode.BAD_REQUEST,Constants.SERIAL_NUMBER_NOT_FOUND);
            } else if (Constants.LAPTOP_IS_ALREADY_EXIST.equals(e.getMessage())) {
                throw new LaptopNotFoundException(ErrorCode.CONFLICT, Constants.LAPTOP_IS_ALREADY_EXIST);
            }
            throw new StudentNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.STUDENT_ADD_FAIL);
        }
    }




    @Override
    public LaptopResponseDTO updateLaptop(LaptopRequestDTO request) {
        log.debug("updating student starts");

        try {
            if(request.getStudentId()== null){
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND, Constants.STUDENT_ID_NOT_FOUND);
            }
            if(request.getId()== null){
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND, Constants.LAPTOP_ID_NOT_FOUND);
            }
            Optional<Student> student = studentRepository.findById(request.getStudentId());

            if (student.isEmpty()) {
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND, Constants.STUDENT_NOT_FOUND);
            }
            Optional<Laptop> laptopEntity = laptopRepository.findById(request.getId());

            if(laptopEntity.isEmpty()){
                throw new LaptopNotFoundException(ErrorCode.NOT_FOUND, Constants.LAPTOP_NOT_FOUND);
            }

            if(laptopRepository.existsBySerialNumber(request.getSerialNumber())){
                throw new LaptopNotFoundException(ErrorCode.BAD_REQUEST, Constants.LAPTOP_IS_ALREADY_EXIST);
            }

            Laptop laptop = modelMapper.map(request,Laptop.class);

            Laptop savedAddress = laptopRepository.save(laptop);

            LaptopResponseDTO response = modelMapper.map(savedAddress,LaptopResponseDTO.class);

            log.debug("updating student  ends");
            return response;
        }catch (Exception e) {
            if(Constants.LAPTOP_IS_ALREADY_EXIST.equals(e.getMessage())) {
                throw new LaptopNotFoundException(ErrorCode.CONFLICT, Constants.LAPTOP_IS_ALREADY_EXIST);
            }else if(Constants.STUDENT_ID_NOT_FOUND.equals(e.getMessage())){
                throw new LaptopNotFoundException(ErrorCode.NOT_FOUND, Constants.STUDENT_ID_NOT_FOUND);
            }else if(Constants.LAPTOP_NOT_FOUND.equals(e.getMessage())){
                throw new LaptopNotFoundException(ErrorCode.NOT_FOUND, Constants.LAPTOP_NOT_FOUND);
            }else if(Constants.STUDENT_NOT_FOUND.equals(e.getMessage())){
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND,Constants.STUDENT_NOT_FOUND);
            }else if(Constants.LAPTOP_ID_NOT_FOUND.equals(e.getMessage())){
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND,Constants.LAPTOP_ID_NOT_FOUND);
            }
            else{
                throw new StudentNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR,Constants.LAPTOP_UPDATE_FAIL);
            }
        }
    }

    @Override
    public StudentResponseDetails findStudentWithLaptops(String firstName) {
        log.debug("fetching student details");

        try {
            if(firstName.isEmpty() ){
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND,Constants.FIRST_NAME_NOT_FOUND);
            }
            StudentResponseDetails response = new StudentResponseDetails();

            List<Laptop> laptopList= studentRepository.findLaptopByFirstName(firstName);

            Student student = studentRepository.findStudentDetailsByFirstName(firstName);

            if (Objects.isNull(student)) {
                throw new StudentNotFoundException(ErrorCode.NO_CONTENT, Constants.EMPTY_STUDENT_LIST); // empty response
            }
            // if laptop is not found give response student details

                response.setId(student.getId());
                response.setFirstName(firstName);
                response.setLastName(student.getLastName());
                response.setMiddleName(student.getMiddleName());
                response.setEmailId(student.getEmailId());
                response.setMobileNumber(student.getMobileNumber());
                List<LaptopResponseModel> laptopResponseModels = new ArrayList<>();

                if (!laptopList.isEmpty()) {

                    laptopResponseModels = laptopList.stream()
                            .map(laptop -> modelMapper.map(laptop, LaptopResponseModel.class))
                            .peek(laptopResponseModel -> log.info("mapped laptopEntity to response {}",laptopResponseModel))
                            .collect(Collectors.toList());
                }

                response.setLaptopResponseModels(laptopResponseModels);
            return response;
        }catch (Exception e){
            if(Constants.FIRST_NAME_NOT_FOUND.equals(e.getMessage())){
                throw new StudentNotFoundException(ErrorCode.NOT_FOUND,Constants.FIRST_NAME_NOT_FOUND);
            }else if(Constants.EMPTY_STUDENT_LIST.equals(e.getMessage())){
                throw new LaptopNotFoundException(ErrorCode.NO_CONTENT, Constants.EMPTY_STUDENT_LIST);
            }
            throw new StudentNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR,Constants.LAPTOP_DETAILS_FETCH_FAIL);
        }
    }


}
