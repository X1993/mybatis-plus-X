package com.github.mybatisplus.config.business;

/**
 * @author wangjj7
 * @date 2022/3/4
 * @description
 */
public enum BusinessIdSqlMethod {


    /**
     * 删除
     */
    DELETE_BY__BUSINESS_ID("deleteByBusinessId", "根据业务ID 删除一条数据", "<script>\nDELETE FROM %s WHERE %s=#{%s}\n</script>"),
    DELETE_BATCH_BY_BUSINESS_IDS("deleteBatchBusinessIds", "根据业务ID集合，批量删除数据", "<script>\nDELETE FROM %s WHERE %s IN (%s)\n</script>"),

    /**
     * 逻辑删除
     */
    LOGIC_DELETE_BY_BUSINESS_ID("deleteByBusinessId", "根据业务ID 逻辑删除一条数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),
    LOGIC_DELETE_BATCH_BY_BUSINESS_IDS("deleteBatchBusinessIds", "根据业务ID集合，批量逻辑删除数据", "<script>\nUPDATE %s %s WHERE %s IN (%s) %s\n</script>"),

    /**
     * 修改
     */
    UPDATE_BY_BUSINESS_ID("updateByBusinessId", "根据业务ID 选择修改数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),

    /**
     * 逻辑删除 -> 修改
     */
    LOGIC_UPDATE_BY_BUSINESS_ID("updateByBusinessId", "根据业务ID 修改数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),

    /**
     * 查询
     */
    SELECT_BY_BUSINESS_ID("selectByBusinessId", "根据业务ID 查询一条数据", "SELECT %s FROM %s WHERE %s=#{%s} %s"),
    SELECT_BATCH_BY_BUSINESS_IDS("selectBatchBusinessIds", "根据业务ID集合，批量查询数据", "<script>SELECT %s FROM %s WHERE %s IN (%s) %s</script>"),
    ;

    private final String method;
    private final String desc;
    private final String sql;

    BusinessIdSqlMethod(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }

}
