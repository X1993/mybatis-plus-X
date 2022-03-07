package com.github.mybatisplus.config.business.no.spring;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.github.mybatisplus.config.business.XSqlInjector;
import com.github.mybatisplus.config.business.no.spring.entity.Person;
import com.github.mybatisplus.config.business.no.spring.mapper.PersonMapper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author X1993
 * @date 2022/3/7
 * @description
 */
public class NoSpringDemo {

    private static SqlSessionFactory sqlSessionFactory = initSqlSessionFactory();

    public static void main(String[] args)
    {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            PersonMapper mapper = session.getMapper(PersonMapper.class);
            Long businessId = 111111L;

            Person person = new Person()
                    .setBusinessId(businessId)
                    .setName("老李");

            mapper.insert(person);
            System.out.println("结果: " + mapper.selectByBusinessId(businessId));

            person.setAge(30);
            mapper.updateByBusinessId(person);
            System.out.println("结果: " + mapper.selectByBusinessId(businessId));

            mapper.deleteByBusinessId(businessId);
            System.out.println("结果: " + mapper.selectByBusinessId(businessId));
        }
    }

    public static SqlSessionFactory initSqlSessionFactory()
    {
        DataSource dataSource = dataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("Production", transactionFactory, dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration(environment);
        GlobalConfigUtils.getGlobalConfig(configuration).setSqlInjector(new XSqlInjector());
        configuration.addMapper(PersonMapper.class);
        configuration.setLogImpl(StdOutImpl.class);
        return new MybatisSqlSessionFactoryBuilder().build(configuration);
    }

    public static DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:mem:test");
        dataSource.setUsername("root");
        dataSource.setPassword("test");
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("create table person (" +
                    "id BIGINT(20) NOT NULL," +
                    "business_id BIGINT(20) NOT NULL," +
                    "name VARCHAR(30) NULL," +
                    "age INT(11) NULL," +
                    "PRIMARY KEY (id)" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

}
