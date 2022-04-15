2022-03-08：公司数据库规范里要求创建表时必须要有单表自增的pk_id和全局唯一业务id，在使用mybatis-plus的过程时，通常使用pk_id作为@TableId，
然而实际做业务开发时，往往又是基于id来操作，所以在BaseMapper的基础上扩展了针对业务id的CRUD操作，配合@BusinessId注解使用。

2022-04-15：在查询历史数据的时候需要忽略逻辑删除的情况，基于mybatis-plus原有自动注入的查询方法扩展。

（使用方式：对于SpringBoot版本，将com.github.mybatisplus.config.XSqlInjector注入Spring容器，
mapper接口继承com.github.mybatisplus.config.XBaseMapper）