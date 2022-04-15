package com.github.mybatisplus.config.business.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.github.mybatisplus.config.business.BusinessIdHelper;
import com.github.mybatisplus.config.business.BusinessIdSqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author X1993
 * @date 2022/3/7
 * @description
 */
public class DeleteByBusinessId extends AbstractMethod
{
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo)
    {
        TableFieldInfo businessIdTableFieldInfo = BusinessIdHelper.getBusinessIdTableFieldInfo(modelClass, tableInfo);

        String sql;
        if (tableInfo.isLogicDelete()) {
            BusinessIdSqlMethod sqlMethod = BusinessIdSqlMethod.LOGIC_DELETE_BY_BUSINESS_ID;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo),
                    businessIdTableFieldInfo.getColumn(), businessIdTableFieldInfo.getProperty(),
                    tableInfo.getLogicDeleteSql(true, true));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return addUpdateMappedStatement(mapperClass, modelClass, getMethod(sqlMethod), sqlSource);
        } else {
            BusinessIdSqlMethod sqlMethod = BusinessIdSqlMethod.DELETE_BY__BUSINESS_ID;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), businessIdTableFieldInfo.getColumn(),
                    businessIdTableFieldInfo.getProperty());
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, getMethod(sqlMethod), sqlSource);
        }
    }

    private String getMethod(BusinessIdSqlMethod sqlMethod){
        return sqlMethod.getMethod();
    }

}

