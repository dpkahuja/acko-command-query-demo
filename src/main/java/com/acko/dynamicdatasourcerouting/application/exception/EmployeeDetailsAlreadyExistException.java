package com.acko.dynamicdatasourcerouting.application.exception;

public class EmployeeDetailsAlreadyExistException extends RuntimeException {
  private final String name;

  public EmployeeDetailsAlreadyExistException(String name) {
    super(name);
    this.name = name;
  }

  @Override
  public String toString() {
    return "employee already exist with name " + this.name;
  }
}
