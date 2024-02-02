package com.acko.dynamicdatasourcerouting.events.employee.models;

import com.acko.dynamicdatasourcerouting.audit.IAuditEventContext;
import com.acko.dynamicdatasourcerouting.audit.UniqueEntityIDString;
import java.util.Date;
import lombok.Data;

@Data
public class LogCreated implements IAuditEventContext {

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
