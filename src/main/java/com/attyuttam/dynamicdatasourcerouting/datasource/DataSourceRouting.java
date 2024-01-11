package com.attyuttam.dynamicdatasourcerouting.datasource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRouting extends AbstractRoutingDataSource {
    private final DataSourceOneConfig dataSourceOneConfig;
    private final DataSourceTwoConfig dataSourceTwoConfig;
    private final DataSourceContextHolder dataSourceContextHolder;

    public DataSourceRouting(DataSourceContextHolder dataSourceContextHolder, DataSourceOneConfig dataSourceOneConfig,
                             DataSourceTwoConfig dataSourceTwoConfig) {
        this.dataSourceOneConfig = dataSourceOneConfig;
        this.dataSourceTwoConfig = dataSourceTwoConfig;
        this.dataSourceContextHolder = dataSourceContextHolder;

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceEnum.DATASOURCE_ONE, dataSourceOneDataSource());
        dataSourceMap.put(DataSourceEnum.DATASOURCE_TWO, dataSourceTwoDataSource());
        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(dataSourceOneDataSource());
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceContextHolder.getBranchContext();
    }

    public DataSource dataSourceOneDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceOneConfig.getUrl());
        dataSource.setUsername(dataSourceOneConfig.getUsername());
        dataSource.setPassword(dataSourceOneConfig.getPassword());
        return dataSource;
    }

    public DataSource dataSourceTwoDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceTwoConfig.getUrl());
        dataSource.setUsername(dataSourceTwoConfig.getUsername());
        dataSource.setPassword(dataSourceTwoConfig.getPassword());
        return dataSource;
    }
}
