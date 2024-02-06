package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.events.employee.models.AllEmployeeDeleted;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AllEmployeeDeletedHandler implements EventHandler<AllEmployeeDeleted> {

  @Override
  public void execute(AllEmployeeDeleted event) {
    log.info("EVENT AllEmployeeDeletedHandler {}", event);
  }
}
