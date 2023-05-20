package com.example.pgCRUDOp.controllers;



import com.example.pgCRUDOp.request.LaptopRequestDTO;
import com.example.pgCRUDOp.request.StudentRequestDto;
import com.example.pgCRUDOp.response.LaptopResponseDTO;
import com.example.pgCRUDOp.response.StudentResponseDetails;
import com.example.pgCRUDOp.response.StudentResponseDto;
import com.example.pgCRUDOp.services.ILaptopService;
import com.example.pgCRUDOp.services.IStudentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private ILaptopService laptopService;

    @Autowired
    private IStudentService studentService;

    @PostMapping("/addStudent")
    public ResponseEntity<StudentResponseDto> addStudent(@RequestBody StudentRequestDto requestDto) {
        StudentResponseDto response = studentService.createStudent(requestDto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update_student")
    public ResponseEntity<StudentResponseDto> updateStudent(@RequestBody StudentRequestDto request) {
        StudentResponseDto response = studentService.updateStudent(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get_details")
    public ResponseEntity<List<StudentResponseDto>> getStudentDetailsByFields(@RequestParam(name = "firstName" , required = false) String firstName, @RequestParam(name = "lastName" , required = false) String lastName, @RequestParam(name ="mobileNumber", required = false) Long mobileNumber){
        List<StudentResponseDto> responses = studentService.findStudentByDetails(firstName ,lastName, mobileNumber);
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/add_laptop")
    public ResponseEntity<LaptopResponseDTO> addLaptop(@RequestBody LaptopRequestDTO request){
        LaptopResponseDTO response = laptopService.addLaptop(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update_laptop")
    public ResponseEntity<LaptopResponseDTO> updateLaptop(@RequestBody LaptopRequestDTO request){
        LaptopResponseDTO responses = laptopService.updateLaptop(request);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/get_laptop_details/{firstName}")
    public ResponseEntity<StudentResponseDetails> getStudentAlongHisLaptops(@PathVariable String firstName){
        StudentResponseDetails response = laptopService.findStudentWithLaptops(firstName);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/get_student/{firstName}")
    public ResponseEntity<StudentResponseDto> getStudentDetails(@PathVariable String firstName){
        StudentResponseDto response = studentService.findStudentDetails(firstName);
        return ResponseEntity.ok().body(response);
    }


}
