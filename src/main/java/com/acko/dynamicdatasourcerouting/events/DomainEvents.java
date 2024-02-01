package com.acko.dynamicdatasourcerouting.events;

import com.acko.dynamicdatasourcerouting.events.employee.EventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DomainEvents {

  private static final Map<String, List<EventHandler>> handlersMap =
      new HashMap<>(); // event name to callback
  private static final List<AuditEvents> markedAggregates =
      new ArrayList<>(); // group of events for one command/aggregate id

  // if aggregate is missing, add it
  public static void markAggregateForDispatch(AuditEvents aggregate) {
    boolean aggregateFound = findMarkedAggregateByID(aggregate.get_id()) != null;

    if (!aggregateFound) {
      markedAggregates.add(aggregate);
    }
  }

  private static void dispatchAggregateEvents(AuditEvents aggregate) {
    aggregate.getDomainEvents().forEach(event -> {
      log.info("dispatch {}", event);
      dispatch(event);});

  }

  private static void removeAggregateFromMarkedDispatchList(AuditEvents aggregate) {
    markedAggregates.removeIf(a -> a.equals(aggregate));
  }

  private static AuditEvents findMarkedAggregateByID(UniqueEntityIDString id) {
    return markedAggregates.stream()
        .filter(aggregate -> aggregate.get_id().equals(id))
        .findFirst()
        .orElse(null);
  }

  public static void dispatchEventsForAggregate(UniqueEntityIDString id) {
    AuditEvents aggregate = findMarkedAggregateByID(id);
  log.info("dispatchEventsForAggregate {} ", aggregate);
    if (aggregate != null) {
      dispatchAggregateEvents(aggregate);
      aggregate.clearEvents();
      removeAggregateFromMarkedDispatchList(aggregate);
    }
  }

  public static <T extends IDomainEvent> void register(
      EventHandler<T> callback, String eventClassName) {
    log.info("register {} ", eventClassName);
    handlersMap.computeIfAbsent(eventClassName, k -> new ArrayList<>()).add(callback);
  }

  public static void clearHandlers() {
    handlersMap.clear();
  }

  public static void clearMarkedAggregates() {
    markedAggregates.clear();
  }

  private static void dispatch(IDomainEvent event) {
    String eventClassName = event.getClass().getSimpleName();
    log.info("handlersMap {}, eventClassName {}", handlersMap
            .getOrDefault(eventClassName, new ArrayList<>()), eventClassName);
    handlersMap
        .getOrDefault(eventClassName, new ArrayList<>())
        .forEach(handler -> {
          log.info("Executiing {} with {}", handler, event);
          handler.execute(event);
        });
  }
}
