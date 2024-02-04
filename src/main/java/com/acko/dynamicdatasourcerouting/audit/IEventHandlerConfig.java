package com.acko.dynamicdatasourcerouting.audit;

import com.acko.dynamicdatasourcerouting.events.employee.EventHandler;

public interface IEventHandlerConfig {

  // Application can register its event handlers here.
  // Using strict class name instead loose string names
  <T extends IAuditEventContext, U extends EventHandler> void registerHandler(
      Class<T> eventClass, U handler);

  void logSubscriptions();
}
