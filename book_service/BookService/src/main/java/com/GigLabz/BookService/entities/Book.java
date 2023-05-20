package com.GigLabz.BookService.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String bookName;
    private String publishedYear;
    private String place;
    private Long studentId;
    private String studentName;

}
