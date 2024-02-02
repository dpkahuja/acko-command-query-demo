package com.acko.dynamicdatasourcerouting.events;

import com.acko.dynamicdatasourcerouting.events.employee.EventHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DomainEvents {

  private static final Map<String, List<EventHandler>> handlersMap =
      new ConcurrentHashMap<>(); // event name to callback
  private static final List<AuditEvents> markedAggregates =
      new CopyOnWriteArrayList<>(); // group of events for one command/aggregate id

  // if aggregate is missing, add it
  public static void markAggregateForDispatch(AuditEvents aggregate) {
    boolean aggregateFound = findMarkedAggregateByID(aggregate.get_id()) != null;

    if (!aggregateFound) {
      markedAggregates.add(aggregate);
    }
  }

  private static void dispatchAggregateEvents(AuditEvents aggregate) {
    aggregate.getDomainEvents().forEach(event -> dispatch(event));
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
    handlersMap
        .getOrDefault(eventClassName, new ArrayList<>())
        .forEach(
            handler -> {
              log.info("Executiing {} with {}", handler, event);
              handler.execute(event);
            });
  }
}
