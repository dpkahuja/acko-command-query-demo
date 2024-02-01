package com.acko.dynamicdatasourcerouting.events.employee.models;

import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.events.IDomainEvent;
import com.acko.dynamicdatasourcerouting.events.UniqueEntityIDString;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class EmployeeCreated implements IDomainEvent {
  private final Employee employee;
  private final String by;

  public EmployeeCreated(Employee employee, String by) {
    super();
    this.employee = employee;
    this.by = by;
  }

  @Override
  public UniqueEntityIDString getAggregateId() {
    return new UniqueEntityIDString(String.valueOf(employee.getEmployeeId()));
  }
}
