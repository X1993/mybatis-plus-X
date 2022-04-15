package com.github.mybatisplus.config.nonLogical;

/**
 * 当查询历史数据的时候，有时候需要忽略逻辑列配置
 * @author X1993
 * @date 2022/4/14
 * @description
 */
public enum IgnoreLogicalSqlMethod {

    SELECT_BY_ID_IGNORE_LOGICAL("selectByIdIgnoreLogical", "根据ID 查询一条数据（忽略逻辑列配置）", "SELECT %s FROM %s WHERE %s=#{%s}"),
    SELECT_BY_MAP_IGNORE_LOGICAL("selectByMapIgnoreLogical", "根据columnMap 查询一条数据（忽略逻辑列配置）", "<script>SELECT %s FROM %s %s\n</script>"),
    SELECT_BATCH_BY_IDS_IGNORE_LOGICAL("selectBatchIdsIgnoreLogical", "根据ID集合，批量查询数据（忽略逻辑列配置）", "<script>SELECT %s FROM %s WHERE %s IN (%s)</script>"),
    SELECT_ONE_IGNORE_LOGICAL("selectOneIgnoreLogical", "查询满足条件一条数据（忽略逻辑列配置）", "<script>%s SELECT %s FROM %s %s %s\n</script>"),
    SELECT_COUNT_IGNORE_LOGICAL("selectCountIgnoreLogical", "查询满足条件总记录数（忽略逻辑列配置）", "<script>%s SELECT COUNT(%s) FROM %s %s %s\n</script>"),
    SELECT_LIST_IGNORE_LOGICAL("selectListIgnoreLogical", "查询满足条件所有数据（忽略逻辑列配置）", "<script>%s SELECT %s FROM %s %s %s\n</script>"),
    SELECT_PAGE_IGNORE_LOGICAL("selectPageIgnoreLogical", "查询满足条件所有数据（并翻页，忽略逻辑列配置）", "<script>%s SELECT %s FROM %s %s %s\n</script>"),
    SELECT_MAPS_IGNORE_LOGICAL("selectMapsIgnoreLogical", "查询满足条件所有数据（忽略逻辑列配置）", "<script>%s SELECT %s FROM %s %s %s\n</script>"),
    SELECT_MAPS_PAGE_IGNORE_LOGICAL("selectMapsPageIgnoreLogical", "查询满足条件所有数据（并翻页，忽略逻辑列配置）", "<script>\n %s SELECT %s FROM %s %s %s\n</script>"),
    SELECT_OBJS_IGNORE_LOGICAL("selectObjsIgnoreLogical", "查询满足条件所有数据（忽略逻辑列配置）", "<script>%s SELECT %s FROM %s %s %s\n</script>");
    ;

    private final String method;
    private final String desc;
    private final String sql;

    IgnoreLogicalSqlMethod(String method, String desc, String sql) {
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
