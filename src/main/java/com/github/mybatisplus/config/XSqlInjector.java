package com.github.mybatisplus.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.github.mybatisplus.config.business.BusinessIdHelper;
import com.github.mybatisplus.config.business.method.*;
import com.github.mybatisplus.config.nonLogical.method.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

/**
 * @author X1993
 * @date 2022/3/7
 * @description
 */
public class XSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass ,tableInfo);
        methodList.addAll(tryGetBusinessIdMethods(mapperClass));
        methodList.addAll(getIgnoreLogicalSelectMethods(tableInfo));
        return methodList;
    }

    private List<AbstractMethod> tryGetBusinessIdMethods(Class<?> mapperClass)
    {
        Class<?> modelClass = ReflectionKit.getSuperClassGenericType(mapperClass, Mapper.class, 0);
        if (modelClass != null && BusinessIdHelper.getBusinessIdField(modelClass) != null) {
            //存在业务id
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

    private List<AbstractMethod> getIgnoreLogicalSelectMethods(TableInfo tableInfo)
    {
        Stream.Builder<AbstractMethod> builder = Stream.<AbstractMethod>builder()
                .add(new SelectByMapIgnoreLogical())
                .add(new SelectCountIgnoreLogical())
                .add(new selectListIgnoreLogical())
                .add(new SelectMapsIgnoreLogical())
                .add(new SelectMapsPageIgnoreLogical())
                .add(new SelectObjsIgnoreLogical())
                .add(new SelectOneIgnoreLogical())
                .add(new SelectPageIgnoreLogical())
                .add(new SelectByBusinessIdIgnoreLogical())
                .add(new SelectBatchByBusinessIdsIgnoreLogical());

        if (tableInfo.havePK()) {
            builder.add(new SelectByIdIgnoreLogical())
                    .add(new SelectBatchByIdsIgnoreLogical());
        } else {
            logger.warn(String.format("%s ,Not found @TableId annotation, Cannot use Mybatis-Plus 'xxById' Method.",
                    tableInfo.getEntityType()));
        }

        return builder.build()
                .collect(toList());
    }

}
