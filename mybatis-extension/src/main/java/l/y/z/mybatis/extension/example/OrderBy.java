package l.y.z.mybatis.extension.example;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * liuyazong
 * 2019/8/25 21:03
 * <p></p>
 */
@Getter
@ToString
public class OrderBy {
    private Map<String, Boolean> orderBy;

    private OrderBy(Map<String, Boolean> orderBy) {
        this.orderBy = orderBy;
    }

    public static OrderBy.Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, Boolean> orderBy;

        public Builder orderBy(String field, Boolean desc) {
            if (null == orderBy) {
                orderBy = new HashMap<>();
            }
            orderBy.put(field, desc);
            return this;
        }

        public OrderBy build() {
            return new OrderBy(this.orderBy);
        }
    }
}
