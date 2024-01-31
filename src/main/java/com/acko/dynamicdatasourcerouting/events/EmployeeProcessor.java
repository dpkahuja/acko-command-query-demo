package com.acko.dynamicdatasourcerouting.events;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Log4j2
public class EmployeeProcessor {
  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
  public void handleEmployeeCreatedEvent(EmployeeCreated event) {
    log.info("publish to kafka {}", event);
  }
}
