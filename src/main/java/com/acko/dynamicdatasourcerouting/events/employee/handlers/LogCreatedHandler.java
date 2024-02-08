package com.acko.dynamicdatasourcerouting.events.employee.handlers;

import com.acko.dynamicdatasourcerouting.audit.Identifier;
import com.acko.dynamicdatasourcerouting.events.employee.models.LogCreated;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class LogCreatedHandler implements Identifier.EventHandler<LogCreated> {
  @Async
  public void execute(LogCreated event) {
    log.info("EVENT handleLogCreatedEvent {}", event.getLog());
  }
}
