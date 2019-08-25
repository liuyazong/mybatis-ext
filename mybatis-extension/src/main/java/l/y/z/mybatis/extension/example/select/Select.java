package l.y.z.mybatis.extension.example.select;

import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * liuyazong
 * 2019/8/25 21:02
 * <p></p>
 */
@Getter
@ToString
public class Select {
    private String[] fields;

    private Select(String[] fields) {
        this.fields = fields;
    }

    public static Select.Builder newBuilder() {
        return new Select.Builder();
    }

    public static class Builder {
        private Set<String> fields = new HashSet<>();

        public Select.Builder field(String field) {
            fields.add(field);
            return this;
        }

        public Select build() {
            return new Select(this.fields.toArray(new String[0]));
        }
    }
}
