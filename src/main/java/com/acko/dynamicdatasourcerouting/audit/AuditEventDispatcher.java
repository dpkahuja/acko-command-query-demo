package com.acko.dynamicdatasourcerouting.audit;

import com.acko.dynamicdatasourcerouting.events.employee.EventHandler;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Data
@RequiredArgsConstructor
@Component
public class AuditEventDispatcher {
  private final AuditEventHandlerConfig auditEventHandlerConfig;

  void dispatchEventsForAggregate(AuditEventGroup aggregate) {
    if (aggregate != null) {
      try {
        dispatchAggregateEvents(aggregate);
      } catch (Exception e) {
        handleDispatchError(aggregate, e);
      }
    }
  }

  private void dispatchAggregateEvents(AuditEventGroup aggregate) {
    aggregate.getDomainEvents().parallelStream().forEach(event -> dispatch(event));
  }

  private void handleDispatchError(AuditEventGroup aggregate, Exception e) {
    log.error("Error dispatching events for aggregate with id {}", aggregate, e);
  }

  private void dispatch(IAuditEventContext event) {
    String eventClassName = event.getClass().getSimpleName();
    List<EventHandler> handlers =
        auditEventHandlerConfig.getHandlersMap().getOrDefault(eventClassName, new ArrayList<>());
    log.info("dispatching audit event {} with {} in {} handlers", eventClassName, event, handlers);
    handlers.forEach(
        handler -> {
          try {
            handler.execute(event);
          } catch (Exception e) {
            log.error(
                "error while executing eventClassName {}, handler {}, with body {}",
                eventClassName,
                handler,
                event);
          }
        });
  }
}
