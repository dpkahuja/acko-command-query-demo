package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.events.DomainEvents;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeCreated;
import com.acko.dynamicdatasourcerouting.events.employee.models.LogCreated;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class EmployeeProcessor {
  private final EmployeeCreatedNotificationHandler employeeCreatedNotificationHandler;
  private final EmployeeCreatedLogHandler employeeCreatedLogHandler;
  private final LogCreatedHandler logCreatedHandler;

  @PostConstruct
  public void setupSubscriptions() {
    log.info("setupSubscriptions EmployeeProcessor");
    DomainEvents.register(
        employeeCreatedNotificationHandler, EmployeeCreated.class.getSimpleName());
    DomainEvents.register(employeeCreatedLogHandler, EmployeeCreated.class.getSimpleName());
    DomainEvents.register(logCreatedHandler, LogCreated.class.getSimpleName());
  }
}
