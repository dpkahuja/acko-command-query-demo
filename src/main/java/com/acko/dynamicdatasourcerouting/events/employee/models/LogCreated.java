package com.acko.dynamicdatasourcerouting.events.employee.models;

import com.acko.dynamicdatasourcerouting.events.IDomainEvent;
import com.acko.dynamicdatasourcerouting.events.UniqueEntityIDString;
import java.util.Date;
import lombok.Data;

@Data
public class LogCreated implements IDomainEvent {

  private final Date createdTimestamp;
  private final String log;
  private final UniqueEntityIDString aggregateId;

  public LogCreated(UniqueEntityIDString aggregateId, String log) {
    this.log = log;
    this.aggregateId = aggregateId;
    this.createdTimestamp = getCreatedTimestamp();
  }

  @Override
  public UniqueEntityIDString getAggregateId() {
    return this.aggregateId;
  }
}
