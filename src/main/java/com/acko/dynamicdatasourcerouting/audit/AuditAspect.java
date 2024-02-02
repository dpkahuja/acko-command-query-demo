package com.acko.dynamicdatasourcerouting.audit;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2) // Set the order if you have multiple aspects
@Log4j2
public class AuditAspect {

  @Around(value = "@annotation(audit)")
  public Object handle(final ProceedingJoinPoint joinPoint, final Audit audit) throws Throwable {
    if (audit.requiredName().equals("")) {
      log.error("audit aspect has missing field `requiredName`");
      return joinPoint.proceed();
    }
    boolean exception = false;
    AuditEventManager auditEventManager =
        new AuditEventManager(audit.requiredName()); // command class name
    try {
      // Your pre-processing logic, e.g., setting the audit object
      AuditContextHolder.set(auditEventManager);
      // Proceed with the original method execution
      return joinPoint.proceed();
      // Your post-processing logic, if needed
    } catch (Exception e) {
      log.error("exception in audit asepect {}", e.getMessage());
      if (audit.dontDispatchOnException()) {
        exception = true;
      }
      throw e;
    } finally {
      // hook to publish all events by audEvent reference id
      if (audit.mode().equals(AuditMode.AUTO) && !exception) {
        auditEventManager.dispatchAllEventsForAggregate();
      }
      AuditContextHolder.clear();
    }
  }
}
