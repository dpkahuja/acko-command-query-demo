package com.attyuttam.dynamicdatasourcerouting.application.exception;

public class EmployeeDetailsAlreadyExistException extends RuntimeException {
    public EmployeeDetailsAlreadyExistException(String message) {
        super(message);
    }

}