package com.github.mybatisplus.config.business.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.github.mybatisplus.config.business.BusinessIdHelper;
import com.github.mybatisplus.config.business.BusinessIdSqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author wangjj7
 * @date 2022/3/7
 * @description
 */
public class SelectBatchByBusinessIds extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        TableFieldInfo businessIdTableFieldInfo = BusinessIdHelper.getBusinessIdTableFieldInfo(modelClass, tableInfo);

        BusinessIdSqlMethod sqlMethod = BusinessIdSqlMethod.SELECT_BATCH_BY_BUSINESS_IDS;
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(sqlMethod.getSql(),
                sqlSelectColumns(tableInfo, false), tableInfo.getTableName(),
                businessIdTableFieldInfo.getColumn(),
                SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA),
                tableInfo.getLogicDeleteSql(true, true)), Object.class);
        return addSelectMappedStatementForTable(mapperClass, getMethod(sqlMethod), sqlSource, tableInfo);
    }

    private String getMethod(BusinessIdSqlMethod sqlMethod){
        return sqlMethod.getMethod();
    }

}
