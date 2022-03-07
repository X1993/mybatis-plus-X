package com.github.mybatisplus.config.business.no.spring.entity;

import com.github.mybatisplus.config.business.BusinessId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author X1993
 * @since 2022-03-07
 */
@Data
@Accessors(chain = true)
public class Person {

    private Long id;

    @BusinessId
    private Long businessId;

    private String name;

    private int age;

}
