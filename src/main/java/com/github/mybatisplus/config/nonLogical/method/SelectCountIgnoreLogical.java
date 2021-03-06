/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.mybatisplus.config.nonLogical.method;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.github.mybatisplus.config.nonLogical.AbstractIgnoreLogicalMethod;
import com.github.mybatisplus.config.nonLogical.IgnoreLogicalSqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 查询满足条件总记录数，忽略逻辑列配置
 *
 * @author X1993
 * @date 2022/4/14
 */
public class SelectCountIgnoreLogical extends AbstractIgnoreLogicalMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        IgnoreLogicalSqlMethod sqlMethod = IgnoreLogicalSqlMethod.SELECT_COUNT_IGNORE_LOGICAL;
        String sql = String.format(sqlMethod.getSql(), sqlFirst(), sqlCount(), tableInfo.getTableName(),
            sqlWhereEntityWrapper(true, tableInfo), sqlComment());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForOther(mapperClass, sqlMethod.getMethod(), sqlSource, Integer.class);
    }


}
