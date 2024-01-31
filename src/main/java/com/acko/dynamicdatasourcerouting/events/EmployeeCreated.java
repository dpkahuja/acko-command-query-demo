package com.acko.dynamicdatasourcerouting.events;

import com.acko.dynamicdatasourcerouting.domain.Employee;

public class EmployeeCreated {
  private final Employee employee;

  public EmployeeCreated(Employee employee) {
    this.employee = employee;
  }
}
