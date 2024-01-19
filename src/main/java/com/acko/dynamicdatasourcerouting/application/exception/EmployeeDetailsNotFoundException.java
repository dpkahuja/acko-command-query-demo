package com.acko.dynamicdatasourcerouting.application.exception;

public class EmployeeDetailsNotFoundException extends RuntimeException {
  public EmployeeDetailsNotFoundException(String message) {
    super(message);
  }
}
