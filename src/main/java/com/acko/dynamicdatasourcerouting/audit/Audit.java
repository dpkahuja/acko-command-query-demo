package com.acko.dynamicdatasourcerouting.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation to enable auditing for a method. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Audit {
  /**
   * Specifies the required name (usually command name) for auditing. It is a compulsory field
   *
   * @return The required name for auditing.
   */
  String requiredName() default "";

  /**
   * Specifies whether to dispatch events AUTO or manually.
   *
   * @return The audit mode for the method.
   */
  AuditMode mode() default AuditMode.AUTO;

  /**
   * Determines whether to dispatch events on exceptions.
   *
   * @return {@code true} if events should not be dispatched on exceptions; {@code false} otherwise.
   */
  boolean dontDispatchOnException() default true;
}
