package com.GigLabz.BookService.client;

import com.GigLabz.BookService.response.StudentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service" , url = "http://localhost:8675/student")
public interface StudentClient {
        @GetMapping("/get_student/{firstName}")
        ResponseEntity<StudentResponseDto> getStudentDetails(@PathVariable String firstName);
}
