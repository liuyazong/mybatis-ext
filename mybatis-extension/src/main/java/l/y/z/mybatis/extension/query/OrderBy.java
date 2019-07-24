package l.y.z.mybatis.extension.query;

public class OrderBy {
    private String field;
    private boolean ascending;

    public OrderBy(String field, boolean ascending) {
        this.field = field;
        this.ascending = ascending;
    }

    public String getField() {
        return field;
    }

    public boolean getAscending() {
        return ascending;
    }
}
