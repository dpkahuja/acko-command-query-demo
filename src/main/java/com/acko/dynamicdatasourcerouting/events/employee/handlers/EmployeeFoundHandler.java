package com.acko.dynamicdatasourcerouting.events.employee.handlers;

import com.acko.dynamicdatasourcerouting.audit.Identifier;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeFound;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class EmployeeFoundHandler implements Identifier.EventHandler<EmployeeFound> {

  @Override
  public void execute(EmployeeFound event) {
    log.info("EVENT EmployeeCreatedLogHandler {}", event);
  }
}
