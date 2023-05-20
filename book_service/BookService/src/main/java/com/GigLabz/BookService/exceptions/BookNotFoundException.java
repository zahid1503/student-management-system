package com.GigLabz.BookService.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private Integer errorCode;
    private String message;

    public BookNotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode.getCode();
        this.message = message;
    }
}
