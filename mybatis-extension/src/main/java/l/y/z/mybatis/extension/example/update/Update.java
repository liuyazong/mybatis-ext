package l.y.z.mybatis.extension.example.update;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class Update {
    private Map<String, Object> fields;

    private Update(Map<String, Object> fields) {
        this.fields = fields;
    }

    public static Update.Builder newBuilder() {
        return new Update.Builder();
    }

    public static class Builder {
        private Map<String, Object> fields = new HashMap<>();

        public Update.Builder field(String field, Object value) {
            fields.put(field, value);
            return this;
        }

        public Update build() {
            return new Update(this.fields);
        }
    }
}