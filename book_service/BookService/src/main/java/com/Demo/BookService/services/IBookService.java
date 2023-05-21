package com.Demo.BookService.services;

import com.Demo.BookService.request.BookRequest;
import com.Demo.BookService.response.BookResponse;

import java.util.List;

public interface IBookService {
    BookResponse addBook(BookRequest bookRequest, String firstName);
    BookResponse updateBook(BookRequest request , Long id);
    List<BookResponse> findBooksByStudentId(Long studentId);
    String deleteBookById(Long id);
}
