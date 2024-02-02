package com.acko.dynamicdatasourcerouting.events;

import java.util.Date;

public interface IDomainEvent {
  default Date getCreatedTimestamp() {
    return new Date();
  }

  UniqueEntityIDString getAggregateId();

}
