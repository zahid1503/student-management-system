package com.GigLabz.BookService.services;

import com.GigLabz.BookService.request.BookRequest;
import com.GigLabz.BookService.response.BookResponse;

import java.util.List;

public interface IBookService {
    BookResponse addBook(BookRequest bookRequest,String firstName);
    BookResponse updateBook(BookRequest request , Long id);

    List<BookResponse> findBooksByStudentId(Long studentId);

    String deleteBookById(Long id);
}
