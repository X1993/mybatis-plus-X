package com.github.mybatisplus.config.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author X19931
 * @date 2022/3/7
 * @description
 */
public interface XBaseMapper<T> extends BaseMapper<T> {

    /**
     * 根据业务id 删除
     *
     * @param businessId 业务id
     */
    int deleteByBusinessId(Serializable businessId);

    /**
     * 删除（根据业务ID 批量删除）
     *
     * @param businessIdList 业务ID列表(不能为 null 以及 empty)
     */
    int deleteBatchBusinessIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> businessIdList);

    /**
     * 根据 业务ID 修改
     *
     * @param entity 实体对象
     */
    int updateByBusinessId(@Param(Constants.ENTITY) T entity);

    /**
     * 根据 业务ID 查询
     *
     * @param businessId 业务ID
     */
    T selectByBusinessId(Serializable businessId);

    /**
     * 查询（根据业务ID 批量查询）
     *
     * @param businessIdList 业务ID列表(不能为 null 以及 empty)
     */
    List<T> selectBatchBusinessIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> businessIdList);


}
