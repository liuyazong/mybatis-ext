package l.y.z.mybatis.extension.example.insert;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * liuyazong
 * 2019/8/25 21:02
 * <p></p>
 */
@Getter
@ToString
public class Insert {
    private Map<String, Object> fields;

    private Insert(Map<String, Object> fields) {
        this.fields = fields;
    }

    public static Insert.Builder newBuilder() {
        return new Insert.Builder();
    }

    public static class Builder {
        private Map<String, Object> fields = new HashMap<>();

        public Insert.Builder field(String field, Object value) {
            fields.put(field, value);
            return this;
        }

        public Insert build() {
            return new Insert(this.fields);
        }
    }
}