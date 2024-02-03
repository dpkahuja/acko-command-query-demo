package com.acko.dynamicdatasourcerouting.audit;

import com.acko.dynamicdatasourcerouting.events.employee.EventHandler;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class AuditEventHandlerConfig implements IEventHandlerConfig {

  private Map<String, List<EventHandler>> handlersMap = new ConcurrentHashMap<>();

  public AuditEventHandlerConfig(Map<String, List<EventHandler>> eventHandlers) {
    this.handlersMap = Collections.unmodifiableMap(eventHandlers);
  }

  @PostConstruct
  public void logSubscriptions() {
    log.info("setup audit subscriptions");
    if (handlersMap != null) {
      handlersMap.forEach(
          (eventClassName, handlers) -> {
            handlers.forEach(
                handler -> log.info("register {} with {} ", eventClassName, handler.getClass()));
          });
    }
  }
}
