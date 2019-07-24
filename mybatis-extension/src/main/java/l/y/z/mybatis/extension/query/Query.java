package l.y.z.mybatis.extension.query;

import java.util.Arrays;

/**
 * author: liuyazong <br>
 * datetime: 2019-07-24 17:53 <br>
 * <p></p>
 */
public class Query {
    private OrderBy[] orderBies;
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    public Query() {
    }

    public Query(OrderBy[] orderBies, Integer pageNum, Integer pageSize) {
        this.orderBies = orderBies;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public OrderBy[] getOrderBies() {
        return orderBies;
    }

    public void setOrderBies(OrderBy[] orderBies) {
        this.orderBies = orderBies;
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
                "orderBies=" + Arrays.toString(orderBies) +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
