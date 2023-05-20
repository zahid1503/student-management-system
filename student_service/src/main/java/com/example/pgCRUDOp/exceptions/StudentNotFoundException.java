package com.example.pgCRUDOp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private Integer errorCode;
    private String message;

    public StudentNotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode.getCode();
        this.message = message;
    }

    public StudentNotFoundException(String message) {
        super(message);
        this.message = message;
    }


    public StudentNotFoundException(Integer error, Exception ex) {
        super(ex);
        this.errorCode = error;
        this.message = ex.getMessage();
    }

    public StudentNotFoundException(Exception ex) {
        super(ex);
        this.message = ex.getMessage();
    }



}
