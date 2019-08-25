package l.y.z.mybatis.extension.query;

import java.util.Arrays;

/**
 * liuyazong
 * 2019/8/25 21:06
 * <p>查询参数</p>
 */
public abstract class Query {
    /**
     * 排序字段
     */
    private OrderByPair[] orderByPairs;
    /**
     * order by 关键字后的字符串，如 column_name1 desc, column_name2 asc。
     * 优先使用orderByPairs。
     * 如果orderByPairs为null，则使用该属性。
     */
    private String orderBy;
    /**
     * 页码，从1开始
     */
    private Integer pageNum;
    /**
     * 每页行数
     */
    private Integer pageSize;

    public Query() {
    }

    protected Query(OrderByPair[] orderByPairs, Integer pageNum, Integer pageSize) {
        this.orderByPairs = orderByPairs;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public OrderByPair[] getOrderByPairs() {
        return orderByPairs;
    }

    public void setOrderByPairs(OrderByPair[] orderByPairs) {
        this.orderByPairs = orderByPairs;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Query{" +
                "orderByPairs=" + Arrays.toString(orderByPairs) +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
