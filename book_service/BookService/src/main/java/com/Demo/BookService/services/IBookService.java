package com.Demo.BookService.services;

import com.Demo.BookService.request.BookRequest;
import com.Demo.BookService.response.BookResponse;

import java.util.List;

public interface IBookService {
    BookResponse addBook(BookRequest bookRequest, String firstName);
    BookResponse updateBook(BookRequest request , Long id);
<<<<<<< HEAD
    List<BookResponse> findBooksByStudentId(Long studentId);
=======

    List<BookResponse> findBooksByStudentId(Long studentId);

>>>>>>> 9b033b6c26cc5149d7db989e901bcf0c9ac4cb02
    String deleteBookById(Long id);
}
