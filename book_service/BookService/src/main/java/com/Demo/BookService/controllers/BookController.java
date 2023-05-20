package com.Demo.BookService.controllers;

import com.Demo.BookService.request.BookRequest;
import com.Demo.BookService.response.BookResponse;
import com.Demo.BookService.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @PostMapping("/addBook/{firstName}")
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest request, @PathVariable("firstName") String firstName) {
        BookResponse response = bookService.addBook(request,firstName);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<BookResponse> updateBook(@RequestBody BookRequest request ,@PathVariable("id") Long id) {
        BookResponse response = bookService.updateBook(request,id);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/get_books/{studentId}")
    ResponseEntity<List<BookResponse>> getBooksByStudentId(@PathVariable("studentId") Long studentId){
        List<BookResponse> responses = bookService.findBooksByStudentId(studentId);
        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("/deleteBook/{id}")
    ResponseEntity<String> deleteBookById(@PathVariable("id") Long id){
        String response = bookService.deleteBookById(id);
        return ResponseEntity.ok().body(response);
    }
}
