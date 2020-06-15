package com.giovanny.study.config.mybatis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @packageName: com.example.demo1.config.mybatis
 * @className: RunMyScripts
 * @description: RunMyScripts
 * @author: YangJun
 * @date: 2020/4/14 16:26
 * @version: v1.0
 **/
@Component
public class RunMyScripts implements CommandLineRunner, Ordered {
    private final
    MyDataSourceProperties myDataSourceProperties;
    private final
    DataSource dataSource;

    /**
     *
     * @param dataSource dataSource
     * @param myDataSourceProperties myDataSourceProperties
     */
    public RunMyScripts(DataSource dataSource, MyDataSourceProperties myDataSourceProperties) {
        this.dataSource = dataSource;
        this.myDataSourceProperties = myDataSourceProperties;
    }

    /**
     * dsfdsf
     * @param args sfds
     */
    @Override
    public void run(String... args) {
        runScripts(getResources(myDataSourceProperties.getSchema()));
    }

    /**
     * fsfd
     * @return sfdsf
     */
    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    /**
     * sfdsfds
     * @param locations fdsfs
     * @return fdsfds
     */
    private List<Resource> getResources(List<String> locations) {
        List<Resource> resources = new ArrayList<>();
        for (String location : locations) {
            for (Resource resource : doGetResources(location)) {
                if (resource.exists()) {
                    resources.add(resource);
                }
            }
        }
        return resources;
    }

    /**
     * dsfdsfds
     * @param location sfdsfs
     * @return fdsfs
     */
    private Resource[] doGetResources(String location) {
        try {
            SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(new DefaultResourceLoader(),
                    Collections.singletonList(location));
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to load resources from " + location, ex);
        }
    }

    /**
     * fdsfds
     * @param resources fdsfsd
     */
    private void runScripts(List<Resource> resources) {
        if (resources.isEmpty()) {
            return;
        }
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(myDataSourceProperties.isContinueOnError());
        populator.setSeparator(myDataSourceProperties.getSeparator());
        if (myDataSourceProperties.getSqlScriptEncoding() != null) {
            populator.setSqlScriptEncoding(myDataSourceProperties.getSqlScriptEncoding().name());
        }
        for (Resource resource : resources) {
            populator.addScript(resource);
        }

        DatabasePopulatorUtils.execute(populator, dataSource);
    }
}
