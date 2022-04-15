package com.github.mybatisplus.config.nonLogical;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;

/**
 * 忽略逻辑列配置 相关的判断
 * @author X1993
 * @date 2022/4/14
 * @description
 */
public abstract class AbstractIgnoreLogicalMethod extends AbstractMethod {

    @Deprecated
    public AbstractIgnoreLogicalMethod() {
        super();
    }

    /**
     * @param methodName 方法名
     * @since 3.5.0
     */
    protected AbstractIgnoreLogicalMethod(String methodName) {
        super(methodName);
    }

    protected String sqlWhereEntityWrapper(boolean newLine, TableInfo table)
    {
        String sqlScript = table.getAllSqlWhere(false, true, WRAPPER_ENTITY_DOT);
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER_ENTITY), true);
        sqlScript += NEWLINE;
        sqlScript += SqlScriptUtils.convertIf(String.format(SqlScriptUtils.convertIf(" AND", String.format("%s and %s", WRAPPER_NONEMPTYOFENTITY, WRAPPER_NONEMPTYOFNORMAL), false) + " ${%s}", WRAPPER_SQLSEGMENT),
                String.format("%s != null and %s != '' and %s", WRAPPER_SQLSEGMENT, WRAPPER_SQLSEGMENT,
                        WRAPPER_NONEMPTYOFWHERE), true);
        sqlScript = SqlScriptUtils.convertWhere(sqlScript) + NEWLINE;
        sqlScript += SqlScriptUtils.convertIf(String.format(" ${%s}", WRAPPER_SQLSEGMENT),
                String.format("%s != null and %s != '' and %s", WRAPPER_SQLSEGMENT, WRAPPER_SQLSEGMENT,
                        WRAPPER_EMPTYOFWHERE), true);
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER), true);
        return newLine ? NEWLINE + sqlScript : sqlScript;
    }

    protected String sqlWhereByMap(TableInfo table)
    {
        String sqlScript = SqlScriptUtils.convertChoose("v == null", " ${k} IS NULL ",
                " ${k} = #{v} ");
        sqlScript = SqlScriptUtils.convertForeach(sqlScript, COLUMN_MAP, "k", "v", "AND");
        sqlScript = SqlScriptUtils.convertWhere(sqlScript);
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null and !%s", COLUMN_MAP,
                COLUMN_MAP_IS_EMPTY), true);
        return sqlScript;
    }

}
