package com.acko.dynamicdatasourcerouting.audit;

public class AuditContextHolder {
  private static final ThreadLocal<AuditEventManager> threadLocal = new ThreadLocal<>();

  public static void clear() {
    threadLocal.remove();
  }

  public static AuditEventManager get() {
    return threadLocal.get();
  }

  public static void set(AuditEventManager auditEventManager) {
    threadLocal.set(auditEventManager);
  }
}
