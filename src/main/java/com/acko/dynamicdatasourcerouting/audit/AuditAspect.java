package com.acko.dynamicdatasourcerouting.audit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2) // Set the order if you have multiple aspects
public class AuditAspect {

  @Around(value = "@annotation(audit)")
  public Object auditAspect(final ProceedingJoinPoint joinPoint, final Audit audit)
      throws Throwable {
    AuditEventManager auditEventManager = new AuditEventManager(audit.requiredName());
    try {
      // Your pre-processing logic, e.g., setting the audit object
      AuditContextHolder.set(auditEventManager);
      // Proceed with the original method execution
      return joinPoint.proceed();
      // Your post-processing logic, if needed
    } finally {
      // hook to publish all events by audEvent reference id
      auditEventManager.dispatchAllEventsForAggregate();
      AuditContextHolder.clear();
    }
  }
}
