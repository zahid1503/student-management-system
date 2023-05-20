package com.Demo.BookService.response;

import lombok.Data;

@Data
public class BookResponse {

    private Long id;

    private String bookName;
    private String publishedYear;
    private String place;
    private Long studentId;
    private String studentName;

}
