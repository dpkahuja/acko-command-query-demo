package com.acko.dynamicdatasourcerouting.audit;

/**
 * Utility class for managing the thread-local audit context. It stores event group which whill be
 * dispatched later.
 */
public final class AuditContextHolder {

  private static final ThreadLocal<AuditEventManager> threadLocal = new ThreadLocal<>();

  private AuditContextHolder() {}

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
