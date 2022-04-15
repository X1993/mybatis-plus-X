package com.github.mybatisplus.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.github.mybatisplus.config.entity.Person;
import com.github.mybatisplus.config.mapper.PersonMapper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * @author X1993
 * @date 2022/3/7
 * @description
 */
public class BusinessIdMethodTest {

    private static PersonMapper personMapper;

    final static long businessId = 100000L;

    static Person initPerson;

    static SqlSession session;

    @BeforeClass
    public static void init()
    {
        SqlSessionFactory sqlSessionFactory = initSqlSessionFactory();
        session = sqlSessionFactory.openSession(true);
        personMapper = session.getMapper(PersonMapper.class);

        Person person = new Person()
                .setBusinessId(businessId)
                .setName("老李");
        personMapper.insert(person);

        initPerson = personMapper.selectById(person.getId());
        Assert.assertNotNull(initPerson);
    }

    @AfterClass
    public static void destory(){
        Connection connection = session.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE person");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        session.close();
    }

    @Test
    public void selectByBusinessIdTest()
    {
        Assert.assertEquals(initPerson ,personMapper.selectByBusinessId(businessId));
    }

    @Test
    public void selectBatchBusinessIdsTest(){
        List<Person> persons = personMapper.selectBatchBusinessIds(Arrays.asList(businessId));
        Assert.assertTrue(persons.size() == 1);
        Assert.assertEquals(persons.get(0) ,initPerson);
    }

    @Test
    public void updateByBusinessIdTest(){
        initPerson.setName("李四");
        personMapper.updateByBusinessId(initPerson);
        Assert.assertEquals(initPerson ,personMapper.selectByBusinessId(businessId));
    }

    @Test
    public void deleteByBusinessIdTest()
    {
        long businessId1 = businessId + 1;
        Person person = new Person()
                .setBusinessId(businessId1)
                .setName("老李");
        personMapper.insert(person);

        Assert.assertNotNull(personMapper.selectById(person.getId()));

        personMapper.deleteByBusinessId(businessId1);

        Assert.assertNull(personMapper.selectById(person.getId()));
    }

    @Test
    public void deleteBatchBusinessIdsTest()
    {
        long businessId2 = businessId + 2;
        Person person = new Person()
                .setBusinessId(businessId2)
                .setName("老李");
        personMapper.insert(person);

        Assert.assertNotNull(personMapper.selectById(person.getId()));

        personMapper.deleteBatchBusinessIds(Arrays.asList(businessId2));

        Assert.assertNull(personMapper.selectById(person.getId()));
    }

    public static SqlSessionFactory initSqlSessionFactory()
    {
        DataSource dataSource = dataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("test", transactionFactory, dataSource);
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
                    "is_delete INT(2) NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (id)" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

}
