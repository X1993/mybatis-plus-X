package com.github.mybatisplus.config.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.github.mybatisplus.config.business.BusinessId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

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

    private Integer age;

    /**
     * 逻辑删除,0.未删除 1.已删除
     */
    @TableLogic(value = "0" ,delval = "1")
    private Boolean isDelete;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(businessId, person.businessId) && Objects.equals(name, person.name) && Objects.equals(age, person.age) && Objects.equals(isDelete, person.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, businessId, name, age, isDelete);
    }
}
