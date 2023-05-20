package com.example.pgCRUDOp.exceptions;

public class LaptopNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private Integer errorCode;
    private String message;

    public LaptopNotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode.getCode();
        this.message = message;
    }
}
