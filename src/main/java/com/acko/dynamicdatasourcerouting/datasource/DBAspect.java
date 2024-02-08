package com.acko.dynamicdatasourcerouting.datasource;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2
public class DBAspect {
  @Autowired DataSourceContextHolder dataSourceContextHolder;

  @Around(value = "@annotation(dBContext)")
  public Object SetDBContext(final ProceedingJoinPoint joinPoint, final DBContext dBContext)
      throws Throwable {
    try {
      DataSourceEnum dbSource = dBContext.source();
      log.debug("Starting db source with {}", dbSource);
      dataSourceContextHolder.setBranchContext(dbSource);
      return joinPoint.proceed();
    } finally {
      log.debug("Call to {} used {}", joinPoint.getSignature(), dBContext.source());
      DataSourceContextHolder.clearBranchContext();
    }
  }
}
