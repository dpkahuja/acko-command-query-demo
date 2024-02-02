package com.acko.dynamicdatasourcerouting.events.employee.models;

import com.acko.dynamicdatasourcerouting.audit.IAuditEventContext;
import com.acko.dynamicdatasourcerouting.audit.UniqueEntityIDString;
import com.acko.dynamicdatasourcerouting.domain.Employee;
import java.util.Date;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmployeeFound implements IAuditEventContext {
  private final UniqueEntityIDString aggregateId;
  private Employee employee;
  private String by;

  @Override
  public Date getCreatedTimestamp() {
    return new Date();
  }

  @Override
  public UniqueEntityIDString getAggregateId() {
    return this.aggregateId;
  }
}
