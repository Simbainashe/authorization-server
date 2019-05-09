package com.zamacloud.commons.uaaserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * @author Fact S Musingarimi
 * 5/7/19
 * 2:41 PM
 */
@Configuration
class Oauth2SchemaConfiguration {
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    @Value("classpath:oauth2-schema.sql")
    private Resource oauth2SchemaScript;

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(oauth2SchemaScript);
        populator.setContinueOnError(true);
        populator.setIgnoreFailedDrops(true);
        return populator;
    }
}
