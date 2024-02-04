package com.acko.dynamicdatasourcerouting.audit;

import com.acko.dynamicdatasourcerouting.events.employee.EventHandler;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Getter
public class AuditEventHandlerConfig implements IEventHandlerConfig {

  // Initialize an empty map to avoid any null checks later
  private final Map<String, List<EventHandler>> handlersMap = new HashMap<>();

  /* We'll provide the implementation of adding handlers to config.
  This would make sure that same event name is used while registration and event dispatch */
  @Override
  public <T extends IAuditEventContext, U extends EventHandler> void registerHandler(
      Class<T> eventClass, U handler) {
    String eventClassName = eventClass.getSimpleName();
    handlersMap.computeIfAbsent(eventClassName, k -> new ArrayList<>()).add(handler);
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
