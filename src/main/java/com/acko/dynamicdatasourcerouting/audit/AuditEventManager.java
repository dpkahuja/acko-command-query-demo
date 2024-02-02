package com.acko.dynamicdatasourcerouting.audit;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class AuditEventManager {

  private final Map<UniqueEntityIDString, AuditEventGroup> auditEventGroupMap =
      new ConcurrentHashMap<>();
  private final UniqueEntityIDString defaultGroupID;
  private final AuditEventHandlerConfig auditEventHandlerConfig;

  public AuditEventManager(String groupName) {
    this.defaultGroupID = new UniqueEntityIDString();
    AuditEventGroup auditEventGroup = new AuditEventGroup(groupName, this.defaultGroupID);
    auditEventGroupMap.put(this.defaultGroupID, auditEventGroup);
    this.auditEventHandlerConfig = BeanAccessor.getBean(AuditEventHandlerConfig.class);
  }

  public AuditEventGroup spawn(String groupName) {
    AuditEventGroup auditEventGroup = new AuditEventGroup(groupName);
    auditEventGroupMap.put(auditEventGroup.getUniqueEntityIDString(), auditEventGroup);
    return auditEventGroup;
  }

  public void addDomainEvent(IAuditEventContext auditEventContext) {
    AuditEventGroup aggregate = auditEventGroupMap.get(this.defaultGroupID);
    aggregate.addDomainEvent(auditEventContext);
  }

  public void addDomainEvent(String id, IAuditEventContext auditEventContext) {
    AuditEventGroup aggregate = auditEventGroupMap.get(id);
    if (aggregate != null) {
      aggregate.addDomainEvent(auditEventContext);
    }
  }

  public void dispatchEventsForAggregate(UniqueEntityIDString id) {
    AuditEventGroup aggregate = findMarkedAggregateByID(id);
    if (aggregate == null) {
      log.info("cannot find aggregate with id {}", id);
      return;
    }
    dispatchAggregateEvents(aggregate);
    removeAggregateFromMarkedDispatchList(aggregate);
  }

  public void dispatchAllEventsForAggregate() {
    auditEventGroupMap
        .keySet()
        .forEach(
            id -> {
              AuditEventGroup aggregate = auditEventGroupMap.get(id);
              if (aggregate != null) {
                dispatchAggregateEvents(aggregate);
                removeAggregateFromMarkedDispatchList(aggregate);
              }
            });
  }

  private void removeAggregateFromMarkedDispatchList(AuditEventGroup aggregate) {
    aggregate.clearEvents();
    auditEventGroupMap.remove(aggregate.getUniqueEntityIDString());
  }

  private AuditEventGroup findMarkedAggregateByID(UniqueEntityIDString id) {
    return auditEventGroupMap.getOrDefault(id, null);
  }

  private void dispatchAggregateEvents(AuditEventGroup aggregate) {
    aggregate.getDomainEvents().parallelStream().forEach(event -> dispatch(event));
  }

  private void dispatch(IAuditEventContext event) {
    String eventClassName = event.getClass().getSimpleName();
    auditEventHandlerConfig
        .getHandlersMap()
        .getOrDefault(eventClassName, new ArrayList<>())
        .forEach(handler -> handler.execute(event));
  }

  public void dispatchEventsForAggregates(ArrayList<UniqueEntityIDString> ids) {
    ids.parallelStream()
        .forEach(
            id -> {
              AuditEventGroup aggregate = findMarkedAggregateByID(id);
              if (aggregate != null) {
                dispatchAggregateEvents(aggregate);
                aggregate.clearEvents();
                removeAggregateFromMarkedDispatchList(aggregate);
              }
            });
  }
}
