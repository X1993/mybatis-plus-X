package com.github.mybatisplus.config.nonLogical.method;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.github.mybatisplus.config.nonLogical.AbstractIgnoreLogicalMethod;
import com.github.mybatisplus.config.nonLogical.IgnoreLogicalSqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 查询满足条件所有数据，忽略逻辑列配置
 *
 * 参考{@link com.baomidou.mybatisplus.core.injector.methods.SelectList}
 *
 * @author X1993
 * @date 2022/4/14
 * @description
 */
public class selectListIgnoreLogical extends AbstractIgnoreLogicalMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        IgnoreLogicalSqlMethod sqlMethod = IgnoreLogicalSqlMethod.SELECT_LIST_IGNORE_LOGICAL;
        String sql = String.format(sqlMethod.getSql(), sqlFirst(),
                sqlSelectColumns(tableInfo, true), tableInfo.getTableName(),
                sqlWhereEntityWrapper(true, tableInfo), sqlComment());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, sqlMethod.getMethod(), sqlSource, tableInfo);
    }
}