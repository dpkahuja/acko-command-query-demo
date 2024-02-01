package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.events.IDomainEvent;

public interface EventHandler<T extends IDomainEvent> {
  void execute(T event);
}
