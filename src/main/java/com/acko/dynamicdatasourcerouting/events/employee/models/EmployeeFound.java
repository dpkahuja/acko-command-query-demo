package com.acko.dynamicdatasourcerouting.events.employee.models;

import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.events.IDomainEvent;
import com.acko.dynamicdatasourcerouting.events.UniqueEntityIDString;
import java.util.Date;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmployeeFound implements IDomainEvent {
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
