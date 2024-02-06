package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeCreated;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class EmployeeCreatedNotificationHandler implements EventHandler<EmployeeCreated> {

  @Autowired private EmployeeCreatedMessageProducer employeeCreatedMessageProducer;

  @Override
  public void execute(EmployeeCreated event) {
    log.info("EVENT EmployeeCreatedNotificationHandler {}", event);
    try {
      employeeCreatedMessageProducer.sendMessage(event);
    } catch (Exception e) {
      log.error(e);
    }
  }
}
