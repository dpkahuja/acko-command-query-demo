package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.events.employee.models.LogCreated;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;

@Log4j2
public class LogCreatedHandler implements EventHandler<LogCreated> {
  @Async
  public void execute(LogCreated event) {
    log.info("EVENT handleLogCreatedEvent {}", event.getLog());
  }
}
