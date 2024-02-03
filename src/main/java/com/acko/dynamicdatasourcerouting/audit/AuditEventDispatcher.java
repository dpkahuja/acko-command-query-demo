package com.acko.dynamicdatasourcerouting.audit;

import java.util.ArrayList;
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
    auditEventHandlerConfig
        .getHandlersMap()
        .getOrDefault(eventClassName, new ArrayList<>())
        .forEach(handler -> handler.execute(event));
  }
}
