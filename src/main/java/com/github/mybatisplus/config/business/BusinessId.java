package com.github.mybatisplus.config.business;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * 标记业务id
 * @author wangjj7
 * @date 2022/3/4
 * @description
 * 这个注解本身不具有 {@link com.baomidou.mybatisplus.annotation.TableField}功能
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {FIELD})
public @interface BusinessId {
}
