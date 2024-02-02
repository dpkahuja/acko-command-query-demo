package com.acko.dynamicdatasourcerouting.audit;

import com.acko.dynamicdatasourcerouting.events.employee.EmployeeCreatedLogHandler;
import com.acko.dynamicdatasourcerouting.events.employee.EmployeeCreatedNotificationHandler;
import com.acko.dynamicdatasourcerouting.events.employee.EventHandler;
import com.acko.dynamicdatasourcerouting.events.employee.LogCreatedHandler;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeCreated;
import com.acko.dynamicdatasourcerouting.events.employee.models.LogCreated;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Log4j2
@Data
public class AuditEventHandlerConfig implements IEventHandlerConfig {
  private final Map<String, List<EventHandler>> handlersMap =
      new ConcurrentHashMap<>(); // event name to callback

  // handlers
  private final EmployeeCreatedNotificationHandler employeeCreatedNotificationHandler;
  private final EmployeeCreatedLogHandler employeeCreatedLogHandler;
  private final LogCreatedHandler logCreatedHandler;

  @PostConstruct
  public void setupSubscriptions() {
    log.info("setupSubscriptions");
    register(employeeCreatedNotificationHandler, EmployeeCreated.class.getSimpleName());
    register(employeeCreatedLogHandler, EmployeeCreated.class.getSimpleName());
    register(logCreatedHandler, LogCreated.class.getSimpleName());
  }

  public <T extends IAuditEventContext> void register(
      EventHandler<T> callback, String eventClassName) {
    log.info("register {} ", eventClassName);
    handlersMap.computeIfAbsent(eventClassName, k -> new ArrayList<>()).add(callback);
  }
}
