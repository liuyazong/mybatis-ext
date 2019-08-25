package l.y.z.mybatis.extension.query;


/**
 * liuyazong
 * 2019/8/25 21:06
 * <p>排序字段</p>
 */
public class OrderByPair {

    /**
     * 实体类属性
     */
    private String field;
    /**
     * 是否升序。true：升序；false：降序
     */
    private boolean ascending;

    public OrderByPair() {
    }

    public OrderByPair(String field, boolean ascending) {
        this.field = field;
        this.ascending = ascending;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public String toString() {
        return "OrderByPair{" +
                "field='" + field + '\'' +
                ", ascending=" + ascending +
                '}';
    }
}
