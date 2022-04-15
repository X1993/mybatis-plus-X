package com.github.mybatisplus.config;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author X19931
 * @date 2022/3/7
 * @description
 */
public interface XBaseMapper<T> extends BaseMapper<T> {

    /*------------------------- 查询的时候忽略逻辑列配置 --------------------------*/

    /**
     * 根据 ID 查询，忽略逻辑列配置
     * 如果没有指定逻辑列，效果等同{@link #selectById(Serializable)}
     *
     * @param id 主键ID
     */
    T selectByIdIgnoreLogical(Serializable id);

    /**
     * 查询（根据ID 批量查询），忽略逻辑列配置
     * 如果没有指定逻辑列，效果等同{@link #selectBatchIds(Collection)}
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    List<T> selectBatchIdsIgnoreLogical(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);

    /**
     * 查询（根据 columnMap 条件），忽略逻辑列配置
     * 如果没有指定逻辑列，效果等同{@link #selectByMap(Map)}
     *
     * @param columnMap 表字段 map 对象
     */
    List<T> selectByMapIgnoreLogical(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);

    /**
     * 根据 entity 条件，查询一条记录，忽略逻辑列配置
     * 如果没有指定逻辑列，效果等同{@link #selectOne(Wrapper)}
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    T selectOneIgnoreLogical(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询总记录数，忽略逻辑列配置
     * 如果没有指定逻辑列，效果等同{@link #selectCount(Wrapper)}
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    Integer selectCountIgnoreLogical(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据 entity 条件，查询全部记录，忽略逻辑列配置
     * 如果没有指定逻辑列，效果等同{@link #selectList(Wrapper)}
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    List<T> selectListIgnoreLogical(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询全部记录，忽略逻辑列配置
     * 如果没有指定逻辑列，效果等同{@link #selectMaps(Wrapper)}
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    List<Map<String, Object>> selectMapsIgnoreLogical(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询全部记录，忽略逻辑列配置
     * <p>注意： 只返回第一个字段的值</p>
     * 如果没有指定逻辑列，效果等同{@link #selectObjs(Wrapper)}
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    List<Object> selectObjsIgnoreLogical(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据 entity 条件，查询全部记录（并翻页，忽略逻辑列配置）
     * 如果没有指定逻辑列，效果等同{@link #selectPage(IPage, Wrapper)}
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    <E extends IPage<T>> E selectPageIgnoreLogical(E page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询全部记录（并翻页，忽略逻辑列配置）
     * 如果没有指定逻辑列，效果等同{@link #selectMapsPage(IPage, Wrapper)}
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类
     */
    <E extends IPage<Map<String, Object>>> E selectMapsPageIgnoreLogical(E page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /*----------------------- 业务id相关方法 ---------------------------*/
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

    /**
     * 根据 业务ID 查询
     * 如果没有指定逻辑列，效果等同{@link #selectByBusinessId(Serializable)}
     * 
     * @param businessId 业务ID
     */
    T selectByBusinessIdIgnoreLogical(Serializable businessId);

    /**
     * 查询（根据业务ID 批量查询），忽略逻辑列配置
     * 如果没有指定逻辑列，效果等同{@link #selectBatchBusinessIds(Collection)}
     *
     * @param businessIdList 业务ID列表(不能为 null 以及 empty)
     */
    List<T> selectBatchBusinessIdsIgnoreLogical(@Param(Constants.COLLECTION) Collection<? extends Serializable> businessIdList);


}
