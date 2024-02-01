package com.acko.dynamicdatasourcerouting.events;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

// a class to store events grouped together by UniqueEntityID (or aggregate/command/group of objects
// - id)
@Data
@Log4j2
public class AuditEvents {

  private final UniqueEntityIDString _id;
  private final Class<?> genericType;
  private final List<IDomainEvent> domainEvents;

  public AuditEvents(Class<?> genericType) {
    this._id = new UniqueEntityIDString();
    this.genericType = genericType;
    this.domainEvents = new ArrayList<>();
  }

  public AuditEvents(Class<?> genericType, UniqueEntityIDString id) {
    this._id = id;
    this.genericType = genericType;
    this.domainEvents = new ArrayList<>();
  }

  public List<IDomainEvent> getDomainEvents() {
    return new ArrayList<>(domainEvents);
  }

  public void addDomainEvent(IDomainEvent domainEvent) {
    this.domainEvents.add(domainEvent);
    DomainEvents.markAggregateForDispatch(this);
    logDomainEventAdded(domainEvent);
  }

  public void clearEvents() {
    this.domainEvents.clear();
  }

  private void logDomainEventAdded(IDomainEvent domainEvent) {
    Class<?> domainEventClass = domainEvent.getClass();
   log.info(
        "[Domain Event Added]: " + genericType + " ==> " + domainEventClass.getSimpleName());
  }
}
