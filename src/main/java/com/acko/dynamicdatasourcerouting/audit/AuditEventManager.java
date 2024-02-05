package com.acko.dynamicdatasourcerouting.audit;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AuditEventManager {

  private final Map<UniqueEntityIDString, AuditEventGroup> auditEventGroupMap =
      new ConcurrentHashMap<>();
  private UniqueEntityIDString defaultGroupID;
  private AuditEventDispatcher auditEventDispatcher;
  private List<String> groupNames;

  // initialize
  public AuditEventManager(String groupName) {
    this.defaultGroupID = new UniqueEntityIDString();
    AuditEventGroup auditEventGroup = new AuditEventGroup(groupName, this.defaultGroupID);
    auditEventGroupMap.put(this.defaultGroupID, auditEventGroup);
    auditEventDispatcher = BeanAccessor.getBean(AuditEventDispatcher.class);
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
