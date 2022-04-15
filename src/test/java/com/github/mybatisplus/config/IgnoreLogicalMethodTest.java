package com.github.mybatisplus.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author X1993
 * @date 2022/3/7
 * @description
 */
public class IgnoreLogicalMethodTest {

    private static PersonMapper personMapper;

    static Person logicalDeletePerson;

    static SqlSession session;

    @BeforeClass
    public static void init()
    {
        SqlSessionFactory sqlSessionFactory = initSqlSessionFactory();
        session = sqlSessionFactory.openSession(true);
        personMapper = session.getMapper(PersonMapper.class);

        Person person = new Person()
                .setBusinessId(100001L)
                .setName("老李");
        personMapper.insert(person);

        logicalDeletePerson = personMapper.selectById(person.getId());
        Assert.assertNotNull(logicalDeletePerson);

        personMapper.deleteById(person);
        Assert.assertNull(personMapper.selectById(person.getId()));
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
    public void selectByIdIgnoreLogicalTest()
    {
        Assert.assertNotNull(personMapper.selectByIdIgnoreLogical(logicalDeletePerson.getId()));
    }

    @Test
    public void selectBatchIdsIgnoreLogicalTest()
    {
        List<Person> persons = personMapper.selectBatchIdsIgnoreLogical(
                Arrays.asList(logicalDeletePerson.getId())
        );
        Assert.assertTrue(persons.size() == 1);
    }

    @Test
    public void selectByMapIgnoreLogicalTest()
    {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("id" , logicalDeletePerson.getId());
        Assert.assertTrue(personMapper.selectByMapIgnoreLogical(columnMap).size() == 1);
    }

    @Test
    public void selectOneIgnoreLogicalTest()
    {
        Assert.assertNotNull(personMapper.selectOneIgnoreLogical(
                new LambdaQueryWrapper<Person>()
                        .eq(Person::getId , logicalDeletePerson.getId()))
        );
    }

    @Test
    public void selectCountIgnoreLogicalTest()
    {
        Assert.assertTrue(personMapper.selectCountIgnoreLogical(new LambdaQueryWrapper<Person>()
                .eq(Person::getId , logicalDeletePerson.getId())) == 1);
    }

    @Test
    public void selectListIgnoreLogicalTest()
    {
        Assert.assertTrue(personMapper.selectListIgnoreLogical(new LambdaQueryWrapper<Person>()
                .eq(Person::getId , logicalDeletePerson.getId())).size() == 1);
    }

    @Test
    public void selectMapsIgnoreLogicalTest()
    {
        Assert.assertTrue(personMapper.selectMapsIgnoreLogical(new LambdaQueryWrapper<Person>()
                .eq(Person::getId , logicalDeletePerson.getId())).size() == 1);
    }

    @Test
    public void selectObjsIgnoreLogicalTest()
    {
        Assert.assertTrue(personMapper.selectObjsIgnoreLogical(new LambdaQueryWrapper<Person>()
                .eq(Person::getId , logicalDeletePerson.getId())).size() == 1);
    }

    @Test
    public void selectPageIgnoreLogicalTest()
    {
        Page<Person> personPage = personMapper.selectPageIgnoreLogical(
                new Page<>(1, 10),
                new LambdaQueryWrapper<Person>()
                        .eq(Person::getId, logicalDeletePerson.getId()));

        Assert.assertTrue(personPage.getRecords().size() == 1);
    }

    @Test
    public void selectMapsPageIgnoreLogicalTest()
    {
        Page<Map<String, Object>> mapPage = personMapper.selectMapsPageIgnoreLogical(
                new Page<>(1, 10),
                new LambdaQueryWrapper<Person>()
                        .eq(Person::getId, logicalDeletePerson.getId()));

        Assert.assertTrue(mapPage.getRecords().size() == 1);
    }

    @Test
    public void selectByBusinessIdIgnoreLogicalTest()
    {
        Assert.assertNotNull(personMapper.selectByBusinessIdIgnoreLogical(
                logicalDeletePerson.getBusinessId()));
    }

    @Test
    public void selectBatchBusinessIdsIgnoreLogicalTest()
    {
        Assert.assertNotNull(personMapper.selectBatchBusinessIdsIgnoreLogical(
                Arrays.asList(logicalDeletePerson.getBusinessId())));
    }

    public static SqlSessionFactory initSqlSessionFactory()
    {
        DataSource dataSource = dataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("test1", transactionFactory, dataSource);
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
