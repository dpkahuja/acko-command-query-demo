package com.acko.dynamicdatasourcerouting.events.employee.handlers;

import com.acko.dynamicdatasourcerouting.audit.Identifier;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeCreated;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class EmployeeCreatedLoggingHandler implements Identifier.EventHandler<EmployeeCreated> {

  @Override
  public void execute(EmployeeCreated event) {
    log.info("EVENT EmployeeCreatedLoggingHandler {}", event);
  }
}
