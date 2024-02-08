package com.acko.dynamicdatasourcerouting.audit;

import java.util.concurrent.ExecutorService;

public interface IEventHandlerConfig {

  // Application can register its event handlers here.
  // Using strict class name instead loose string names
  <T extends IAuditEventContext, U extends Identifier.EventHandler> void registerHandler(
      Class<T> eventClass, U handler);

  void registerThreadExecutor(ExecutorService executorService);

  void logSubscriptions();
}
