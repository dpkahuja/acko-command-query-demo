package com.acko.dynamicdatasourcerouting.audit;

import java.util.Date;

public interface IAuditEventContext {
  default Date getCreatedTimestamp() {
    return new Date();
  }

  UniqueEntityIDString getAggregateId();
}
