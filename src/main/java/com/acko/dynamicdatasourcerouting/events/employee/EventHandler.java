package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.audit.IAuditEventContext;

public interface EventHandler<T extends IAuditEventContext> {
  void execute(T event);
}
