package com.github.mybatisplus.config.business;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.github.mybatisplus.config.business.method.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author wangjj7
 * @date 2022/3/7
 * @description
 */
public class XSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass ,tableInfo);
        methodList.addAll(tryGetBusinessIdMethods(mapperClass));
        return methodList;
    }

    private List<AbstractMethod> tryGetBusinessIdMethods(Class<?> mapperClass)
    {
        Class<?> modelClass = ReflectionKit.getSuperClassGenericType(mapperClass, Mapper.class, 0);
        if (modelClass != null && BusinessIdHelper.getBusinessIdField(modelClass) != null) {
            return Arrays.asList(
                    new SelectByBusinessId(),
                    new SelectBatchByBusinessIds(),
                    new DeleteByBusinessId(),
                    new DeleteBatchByBusinessIds(),
                    new UpdateByBusinessId()
            );
        }
        return Collections.EMPTY_LIST;
    }

}
