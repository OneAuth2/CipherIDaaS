package com.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * ISV自己的业务数据源配置
 */
@Configuration
@MapperScan(basePackages = "com.mapper.sf", sqlSessionTemplateRef  = "sfSqlSessionTemplate")
public class sfDataSourceConfig {
    /**
     * dataSource
     */
    @Bean(name = "sfDataSource")
    @ConfigurationProperties(prefix = "datasource.sf")
    public DataSource sfDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * sqlSessionFactory
     * @param dataSource    数据源
     */
    @Bean(name = "sfSqlSessionFactory")
    public SqlSessionFactory sfSqlSessionFactory(@Qualifier("sfDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/sf/*.xml"));
        return bean.getObject();
    }

    /**
     * dataSourceTransactionManager
     * @param dataSource    数据源
     */
    @Bean(name = "sfTransactionManager")
    public DataSourceTransactionManager sfTransactionManager(@Qualifier("sfDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * sqlSessionTemplate
     * @param sqlSessionFactory  sqlsession
     */
    @Bean(name = "sfSqlSessionTemplate")
    public SqlSessionTemplate sfSqlSessionTemplate(@Qualifier("sfSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
