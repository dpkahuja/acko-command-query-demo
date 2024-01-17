package com.acko.dynamicdatasourcerouting.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DBAspect {
    @Autowired
    DataSourceContextHolder dataSourceContextHolder;

    @Around(value = "@annotation(dBContext)")
    public Object SetDBContext(final ProceedingJoinPoint joinPoint, final DBContext dBContext)
            throws Throwable {
        try {
            DataSourceEnum dbSource = dBContext.source();
            System.out.println("Starting db source with " + dbSource);
            dataSourceContextHolder.setBranchContext(dbSource);
            return joinPoint.proceed();
        } finally {
            System.out.println(
                    "Call to " + joinPoint.getSignature() + " used " + dBContext.source());
            DataSourceContextHolder.clearBranchContext();
        }
    }
}
