package com.acko.dynamicdatasourcerouting.events.employee.models;

import com.acko.dynamicdatasourcerouting.audit.IAuditEventContext;
import com.acko.dynamicdatasourcerouting.audit.UniqueEntityIDString;
import com.acko.dynamicdatasourcerouting.domain.Employee;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class EmployeeCreated implements IAuditEventContext {
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
