package com.acko.dynamicdatasourcerouting.audit;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

// a class to store events grouped together by UniqueEntityID (or aggregate/command/group of
// objects_id) during any process
@Data
@Log4j2
public class AuditEventGroup {

  private UniqueEntityIDString uniqueEntityIDString;
  private String groupName;
  private List<IAuditEventContext> domainEvents;

  public AuditEventGroup(String groupName) {
    this.groupName = groupName;
    this.uniqueEntityIDString = new UniqueEntityIDString();
    this.domainEvents = new ArrayList<>();
  }

  public AuditEventGroup(String groupName, UniqueEntityIDString uniqueEntityIDString) {
    this.groupName = groupName;
    this.uniqueEntityIDString = uniqueEntityIDString;
    this.domainEvents = new ArrayList<>();
  }

  public List<IAuditEventContext> getDomainEvents() {
    return new ArrayList<>(domainEvents);
  }

  public void addDomainEvent(IAuditEventContext domainEvent) {
    this.domainEvents.add(domainEvent);
    logDomainEventAdded(domainEvent);
  }

  public void clearEvents() {
    this.domainEvents.clear();
  }

  private void logDomainEventAdded(IAuditEventContext domainEvent) {
    Class<?> domainEventClass = domainEvent.getClass();
    log.info("[Domain Event Added]: " + groupName + " ==> " + domainEventClass.getSimpleName());
  }
}
