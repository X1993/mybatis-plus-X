package com.github.mybatisplus.config.business.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.github.mybatisplus.config.business.BusinessIdHelper;
import com.github.mybatisplus.config.business.BusinessIdSqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author wangjj7
 * @date 2022/3/7
 * @description
 */
public class UpdateByBusinessId extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        TableFieldInfo businessIdTableFieldInfo = BusinessIdHelper.getBusinessIdTableFieldInfo(modelClass, tableInfo);

        boolean logicDelete = tableInfo.isLogicDelete();
        BusinessIdSqlMethod sqlMethod = BusinessIdSqlMethod.UPDATE_BY_BUSINESS_ID;
        final String additional = optlockVersion(tableInfo) + tableInfo.getLogicDeleteSql(true, true);
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
                sqlSet(logicDelete, false, tableInfo, false, ENTITY, ENTITY_DOT),
                businessIdTableFieldInfo.getColumn(), ENTITY_DOT + businessIdTableFieldInfo.getProperty(), additional);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, getMethod(sqlMethod), sqlSource);
    }

    private String getMethod(BusinessIdSqlMethod sqlMethod){
        return sqlMethod.getMethod();
    }
}
