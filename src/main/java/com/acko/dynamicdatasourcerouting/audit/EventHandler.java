package com.acko.dynamicdatasourcerouting.audit;


public interface EventHandler<T extends IAuditEventContext> {
  void execute(T event);
}
