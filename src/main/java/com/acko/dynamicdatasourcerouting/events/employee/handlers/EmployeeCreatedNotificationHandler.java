package com.acko.dynamicdatasourcerouting.events.employee.handlers;

import com.acko.dynamicdatasourcerouting.audit.Identifier;
import com.acko.dynamicdatasourcerouting.events.employee.EmployeeCreatedMessageProducer;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeCreated;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class EmployeeCreatedNotificationHandler
    implements Identifier.EventHandler<EmployeeCreated> {

  @Autowired private EmployeeCreatedMessageProducer employeeCreatedMessageProducer;

  @Override
  public void execute(EmployeeCreated event) {
    log.info("EVENT EmployeeCreatedNotificationHandler {}", event);
    try {
      employeeCreatedMessageProducer
          .sendMessage(event)
          .whenComplete(
              (result, ex) -> {
                if (ex == null) {
                  System.out.println("Sent message=[" + event + "]");
                } else {
                  System.out.println("Unable to send message=[" + event);
                }
              });
    } catch (Exception e) {
      log.error(e);
    }
  }
}
