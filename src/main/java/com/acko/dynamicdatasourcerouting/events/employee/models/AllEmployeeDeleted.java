package com.acko.dynamicdatasourcerouting.events.employee.models;

import com.acko.dynamicdatasourcerouting.events.IDomainEvent;
import com.acko.dynamicdatasourcerouting.events.UniqueEntityIDString;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class AllEmployeeDeleted implements IDomainEvent {
  private final String by;
  private final UniqueEntityIDString aggregateId;

  public AllEmployeeDeleted(UniqueEntityIDString aggregateId, String by) {
    super();
    this.aggregateId = aggregateId;
    this.by = by;
  }

  @Override
  public UniqueEntityIDString getAggregateId() {
    return this.aggregateId;
  }
}
