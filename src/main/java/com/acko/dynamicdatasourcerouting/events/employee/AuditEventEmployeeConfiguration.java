package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.audit.AuditEventHandlerConfig;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeCreated;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeFound;
import com.acko.dynamicdatasourcerouting.events.employee.models.LogCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AuditEventEmployeeConfiguration {

  private final EmployeeCreatedNotificationHandler employeeCreatedNotificationHandler;
  private final EmployeeFoundHandler employeeFound;
  private final LogCreatedHandler logCreatedHandler;

  /* Employee class can simply register its own handlers and create a bean for the config class
  This bean would then be used by our dispatcher for dispatching events to the configured handler */
  @Bean
  public AuditEventHandlerConfig auditEventHandlerConfig() {
    AuditEventHandlerConfig config = new AuditEventHandlerConfig();
    config.registerHandler(EmployeeCreated.class, employeeCreatedNotificationHandler);
    config.registerHandler(EmployeeFound.class, employeeFound);
    config.registerHandler(LogCreated.class, logCreatedHandler);
    return config;
  }
}
