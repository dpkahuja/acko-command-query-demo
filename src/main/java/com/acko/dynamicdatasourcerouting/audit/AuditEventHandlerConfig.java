package com.acko.dynamicdatasourcerouting.audit;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class AuditEventHandlerConfig implements IEventHandlerConfig {

  // Initialize an empty map to avoid any null checks later
  private final Map<String, List<Identifier.EventHandler>> handlersMap = new HashMap<>();

  private ExecutorService executorService = Executors.newFixedThreadPool(100);

  /* We'll provide the implementation of adding handlers to config.
  This would make sure that same event name is used while registration and event dispatch */
  @Override
  public <T extends IAuditEventContext, U extends Identifier.EventHandler> void registerHandler(
      Class<T> eventClass, U handler) {
    String eventClassName = eventClass.getSimpleName();
    handlersMap.computeIfAbsent(eventClassName, k -> new ArrayList<>()).add(handler);
  }

  @Override
  public void registerThreadExecutor(ExecutorService executorService) {
    this.executorService = executorService;
  }

  @PostConstruct
  public void logSubscriptions() {
    log.info("setup audit subscriptions");
    handlersMap.forEach(
        (eventClassName, handlers) ->
            handlers.forEach(
                handler -> log.info("register {} with {} ", eventClassName, handler.getClass())));
  }
}
