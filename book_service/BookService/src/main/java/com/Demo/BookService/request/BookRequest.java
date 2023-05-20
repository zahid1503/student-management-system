package com.Demo.BookService.request;

import lombok.Data;

@Data
public class BookRequest {

    private Long id;

    private String bookName;
    private String publishedYear;
    private String place;
    private Long studentId;
    private String studentName;
}
