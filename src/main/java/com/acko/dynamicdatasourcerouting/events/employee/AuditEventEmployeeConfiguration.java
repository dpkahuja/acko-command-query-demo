package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.audit.AuditEventHandlerConfig;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeFound;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditEventEmployeeConfiguration {
  @Bean
  public AuditEventHandlerConfig auditEventHandlerConfig(
      Map<String, List<EventHandler>> eventHandlers) {
    AuditEventHandlerConfig config = new AuditEventHandlerConfig(eventHandlers);
    return config;
  }

  @Bean
  public Map<String, List<EventHandler>> eventHandlers(
      EmployeeCreatedNotificationHandler employeeCreatedNotificationHandler,
      EmployeeFoundHandler employeeFound,
      LogCreatedHandler logCreatedHandler) {
    Map<String, List<EventHandler>> handlersMap = new HashMap<>();

    handlersMap.put(
        "EmployeeCreated",
        Arrays.asList(employeeCreatedNotificationHandler));
    handlersMap.put(
            "EmployeeFound",
            Arrays.asList(employeeFound));
    handlersMap.put("LogCreated", Arrays.asList(logCreatedHandler));

    return handlersMap;
  }
}
