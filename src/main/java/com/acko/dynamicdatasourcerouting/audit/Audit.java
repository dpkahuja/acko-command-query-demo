package com.acko.dynamicdatasourcerouting.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Audit {
  String requiredName() default "";

  AuditMode mode() default AuditMode.AUTO;

  boolean dispatchOnException() default false;
}
