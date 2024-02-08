package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.audit.AuditEventHandlerConfig;
import com.acko.dynamicdatasourcerouting.events.employee.handlers.*;
import com.acko.dynamicdatasourcerouting.events.employee.models.AllEmployeeDeleted;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeCreated;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeFound;
import com.acko.dynamicdatasourcerouting.events.employee.models.LogCreated;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AuditEventEmployeeConfiguration {

  private final EmployeeCreatedNotificationHandler employeeCreatedNotificationHandler;
  private final EmployeeFoundHandler employeeFound;
  private final LogCreatedHandler logCreatedHandler;
  private final AllEmployeeDeletedHandler allEmployeeDeletedHandler;
  private final EmployeeCreatedLoggingHandler employeeCreatedLoggingHandler;

  /* Employee class can simply register its own handlers and create a bean for the config class
  This bean would then be used by our dispatcher for dispatching events to the configured handler */
  @Bean
  public AuditEventHandlerConfig auditEventHandlerConfig() {
    AuditEventHandlerConfig config = new AuditEventHandlerConfig();
    config.registerHandler(EmployeeCreated.class, employeeCreatedNotificationHandler);
    config.registerHandler(EmployeeCreated.class, employeeCreatedLoggingHandler);
    config.registerHandler(EmployeeFound.class, employeeFound);
    config.registerHandler(LogCreated.class, logCreatedHandler);
    config.registerHandler(AllEmployeeDeleted.class, allEmployeeDeletedHandler);
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    config.registerThreadExecutor(executorService);
    return config;
  }
}
