package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeCreated;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class EmployeeCreatedLogHandler implements EventHandler<EmployeeCreated> {

  @Override
  public void execute(EmployeeCreated event) {
    log.info("EVENT EmployeeCreatedLogHandler {}", event);
  }
}
