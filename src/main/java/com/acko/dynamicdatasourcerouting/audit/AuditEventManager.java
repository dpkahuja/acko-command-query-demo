package com.acko.dynamicdatasourcerouting.audit;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditEventManager {

  private final Map<UniqueEntityIDString, AuditEventGroup> auditEventGroupMap =
      new ConcurrentHashMap<>();
  private UniqueEntityIDString defaultGroupID;
  private AuditEventDispatcher auditEventDispatcher;
  private List<String> groupNames;

  // initialize
  public AuditEventManager(String groupName) {
    AuditEventGroup auditEventGroup = new AuditEventGroup(groupName);
    this.defaultGroupID = auditEventGroup.getUniqueEntityIDString();
    auditEventGroupMap.put(this.defaultGroupID, auditEventGroup);
    auditEventDispatcher = BeanAccessor.getBean(AuditEventDispatcher.class);
  }

  /*
   * new event group for same command group, this is for using outside the audit aspect
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

  public void addDomainEvent(String groupID, IAuditEventContext auditEventContext) {
    AuditEventGroup aggregate = auditEventGroupMap.get(groupID);
    if (aggregate != null) {
      aggregate.addDomainEvent(auditEventContext);
    }
  }

  public void dispatchEventsForSingleAggregate(UniqueEntityIDString id) {
    this.auditEventDispatcher.dispatchEventsForAggregate(findMarkedAggregateByID(id));
    removeAggregateFromMarkedDispatchList(findMarkedAggregateByID(id));
  }

  public void dispatchAllEventsForAggregate() {
    auditEventGroupMap
        .keySet()
        .forEach(
            id ->
                this.auditEventDispatcher.dispatchEventsForAggregate(findMarkedAggregateByID(id)));
  }

  private void removeAggregateFromMarkedDispatchList(AuditEventGroup aggregate) {
    aggregate.clearEvents();
    auditEventGroupMap.remove(aggregate.getUniqueEntityIDString());
  }

  private AuditEventGroup findMarkedAggregateByID(UniqueEntityIDString id) {
    return auditEventGroupMap.getOrDefault(id, null);
  }

  public AuditEventGroup findAndValidateAggregate(UniqueEntityIDString id) {
    AuditEventGroup aggregate = findMarkedAggregateByID(id);
    if (aggregate == null) {
      log.info("Cannot find aggregate with id {}", id);
      return null;
    }
    return aggregate;
  }
}
