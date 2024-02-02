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

  // constructor
  public AuditEventManager(String groupName) {
    this.defaultGroupID = new UniqueEntityIDString();
    AuditEventGroup auditEventGroup = new AuditEventGroup(groupName, this.defaultGroupID);
    auditEventGroupMap.put(this.defaultGroupID, auditEventGroup);
    this.auditEventHandlerConfig = BeanAccessor.getBean(AuditEventHandlerConfig.class);
  }

  /*
  * new event group for same command group
   */
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

  public void dispatchEventsForSingleAggregate(UniqueEntityIDString id) {
    dispatchEventsForAggregate(id);
  }

  public void dispatchAllEventsForAggregate() {
    auditEventGroupMap.keySet().forEach(this::dispatchEventsForAggregate);
  }

  private void dispatchEventsForAggregate(UniqueEntityIDString id) {
    AuditEventGroup aggregate = findAndValidateAggregate(id);
    if (aggregate != null) {
      try {
        dispatchAggregateEvents(aggregate);
        removeAggregateFromMarkedDispatchList(aggregate);
      } catch (Exception e) {
        handleDispatchError(id, e);
      }
    }
  }

  private void handleDispatchError(UniqueEntityIDString id, Exception e) {
    log.error("Error dispatching events for aggregate with id {}", id, e);
  }

  private AuditEventGroup findAndValidateAggregate(UniqueEntityIDString id) {
    AuditEventGroup aggregate = findMarkedAggregateByID(id);
    if (aggregate == null) {
      log.info("Cannot find aggregate with id {}", id);
      return null;
    }
    return aggregate;
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

}
