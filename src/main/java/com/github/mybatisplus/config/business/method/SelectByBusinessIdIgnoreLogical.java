package com.github.mybatisplus.config.business.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.github.mybatisplus.config.business.BusinessIdHelper;
import com.github.mybatisplus.config.business.BusinessIdSqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

/**
 * @author X1993
 * @date 2022/3/4
 * @description
 */
public class SelectByBusinessIdIgnoreLogical extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass,
                                                 Class<?> modelClass,
                                                 TableInfo tableInfo)
    {
        TableFieldInfo businessIdTableFieldInfo = BusinessIdHelper.getBusinessIdTableFieldInfo(modelClass, tableInfo);

        BusinessIdSqlMethod sqlMethod = BusinessIdSqlMethod.SELECT_BY_BUSINESS_ID_IGNORE_LOGICAL;
        SqlSource sqlSource = new RawSqlSource(
                configuration,
                String.format(
                        sqlMethod.getSql(),
                        sqlSelectColumns(tableInfo, false),
                        tableInfo.getTableName(),
                        businessIdTableFieldInfo.getColumn(),
                        businessIdTableFieldInfo.getProperty()
                ), Object.class);

        return this.addSelectMappedStatementForTable(mapperClass, sqlMethod.getMethod(), sqlSource, tableInfo);
    }


}
