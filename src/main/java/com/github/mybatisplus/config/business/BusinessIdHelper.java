package com.github.mybatisplus.config.business;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjj7
 * @date 2022/3/4
 * @description
 */
public class BusinessIdHelper {

    public static TableFieldInfo getBusinessIdTableFieldInfo(Class<?> modelClass ,TableInfo tableInfo)
    {
        Field businessIdField = getBusinessIdField(modelClass);
        if (businessIdField == null){
            return null;
        }

        TableFieldInfo tableFieldInfo = tableInfo.getFieldList()
                .stream()
                .filter(tfi -> businessIdField.equals(tfi.getField()))
                .findFirst()
                .orElse(null);

        if (tableFieldInfo == null)
        {
            ExceptionUtils.mpe("businessId field {} isn't table field" ,tableFieldInfo.getField());
            return null;
        }
        return tableFieldInfo;
    }

    /**
     * 获取业务Id属性
     * @param modelClass
     * @return
     */
    public static Field getBusinessIdField(Class<?> modelClass)
    {
        List<Field> fieldList = ReflectionKit.getFieldList(ClassUtils.getUserClass(modelClass));
        fieldList = fieldList.stream()
                .filter(field -> field.getAnnotation(BusinessId.class) != null)
                .collect(Collectors.toList());

        if (fieldList.isEmpty()){
            return null;
        }else if (fieldList.size() > 1){
            ExceptionUtils.mpe("model {} can't define two BusinessId" ,modelClass);
            return null;
        }

        return fieldList.get(0);
    }

}
