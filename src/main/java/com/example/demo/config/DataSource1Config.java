package com.example.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
//注册到springboot容器，相当于原来xml文件里的<beans>
//下面要进行扫包，目的是标清楚为谁添加的数据源，这样对应的包里函数执行数据库操作的时候，就知道要执行的数据库账号
//密码，表，以及事务处理之类的。
@MapperScan(basePackages= {"com.example.demo.csd.dao"},sqlSessionFactoryRef="test1SqlSessionFactory")
public class DataSource1Config {
    @Bean(name = "test1DataSource")
    @ConfigurationProperties(prefix="spring.datasource.test1")//表示取application.yml配置文件中的名称
    @Primary//primary是设置优先，因为有多个数据源，在没有明确指定用哪个的情况下，会用带有primary的，这个注解必须有一个数据源要添加
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="test1SqlSessionFactory")
    @Primary
    @Qualifier("test1DataSource")//含义是告诉他使用哪个DataSource
    public SqlSessionFactory testSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        // 如果不写这里，会导致 mybatis 出现 invalid bound statement (not found) 的问题
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/config/mybatis-config.xml"));
//        bean.setTypeAliasesPackage();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name="test1TransactionManager")//配置事务
    @Primary
    @Qualifier("test1DataSource")
    public DataSourceTransactionManager testTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name="test1SqlSessionTemplate")
    @Primary
    @Qualifier("test1SqlSessionFactory")
    public SqlSessionTemplate testSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
