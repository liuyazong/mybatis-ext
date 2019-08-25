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
public class Where {


    private Map<String, Condition<Object[]>> in;
    private Map<String, Condition<Object[]>> notIn;
    private Map<String, Condition<Object>> equal;
    private Map<String, Condition<Object>> notEqual;
    private Map<String, Condition<Object[]>> between;
    private Map<String, Condition<Object>> greaterThanOrEqual;
    private Map<String, Condition<Object>> lessThanOrEqual;
    private Map<String, Condition<Object>> greaterThan;
    private Map<String, Condition<Object>> lessThan;

    private Where(Map<String, Condition<Object[]>> in,
                  Map<String, Condition<Object[]>> notIn,
                  Map<String, Condition<Object>> equal,
                  Map<String, Condition<Object>> notEqual,
                  Map<String, Condition<Object[]>> between,
                  Map<String, Condition<Object>> greaterThanOrEqual,
                  Map<String, Condition<Object>> lessThanOrEqual,
                  Map<String, Condition<Object>> greaterThan,
                  Map<String, Condition<Object>> lessThan) {
        this.in = in;
        this.notIn = notIn;
        this.equal = equal;
        this.notEqual = notEqual;
        this.between = between;
        this.greaterThanOrEqual = greaterThanOrEqual;
        this.lessThanOrEqual = lessThanOrEqual;
        this.greaterThan = greaterThan;
        this.lessThan = lessThan;
    }

    public static Where.Builder newBuilder() {
        return new Where.Builder();
    }

    @Getter
    @ToString
    public static class Condition<T> {
        private boolean andOr;
        private T object;

        Condition(boolean andOr, T object) {
            this.andOr = andOr;
            this.object = object;
        }
    }

    public static class Builder {

        private Map<String, Condition<Object[]>> in;
        private Map<String, Condition<Object[]>> notIn;
        private Map<String, Condition<Object>> equal;
        private Map<String, Condition<Object>> notEqual;
        private Map<String, Condition<Object[]>> between;
        private Map<String, Condition<Object>> greaterThanOrEqual;
        private Map<String, Condition<Object>> lessThanOrEqual;
        private Map<String, Condition<Object>> greaterThan;
        private Map<String, Condition<Object>> lessThan;
        private boolean andOr = true;

        public Where.Builder and() {
            this.andOr = true;
            return this;
        }

        public Where.Builder or() {
            this.andOr = false;
            return this;
        }

        public Where.Builder in(String field, Object[] value) {
            if (null == in) {
                in = new HashMap<>();
            }
            in.put(field, new Condition<>(andOr, value));
            return this;
        }

        public Where.Builder notIn(String field, Object[] value) {
            if (null == notIn) {
                notIn = new HashMap<>();
            }
            notIn.put(field, new Condition<>(andOr, value));
            return this;
        }

        public Where.Builder equal(String field, Object value) {
            if (null == equal) {
                equal = new HashMap<>();
            }
            equal.put(field, new Condition<>(andOr, value));
            return this;
        }


        public Where.Builder notEqual(String field, Object value) {
            if (null == notEqual) {
                notEqual = new HashMap<>();
            }
            notEqual.put(field, new Condition<>(andOr, value));
            return this;
        }

        public Where.Builder between(String field, Object start, Object end) {
            if (null == between) {
                between = new HashMap<>();
            }
            between.put(field, new Condition<>(andOr, new Object[]{start, end}));
            return this;
        }

        public Where.Builder greaterThanOrEqual(String field, Object value) {
            if (null == greaterThanOrEqual) {
                greaterThanOrEqual = new HashMap<>();
            }
            greaterThanOrEqual.put(field, new Condition<>(andOr, value));
            return this;
        }

        public Where.Builder lessThanOrEqual(String field, Object value) {
            if (null == lessThanOrEqual) {
                lessThanOrEqual = new HashMap<>();
            }
            lessThanOrEqual.put(field, new Condition<>(andOr, value));
            return this;
        }

        public Where.Builder greaterThan(String field, Object value) {
            if (null == greaterThan) {
                greaterThan = new HashMap<>();
            }
            greaterThan.put(field, new Condition<>(andOr, value));
            return this;
        }

        public Where.Builder lessThan(String field, Object value) {
            if (null == lessThan) {
                lessThan = new HashMap<>();
            }
            lessThan.put(field, new Condition<>(andOr, value));
            return this;
        }

        public Where build() {
            return new Where(in, notIn, equal, notEqual, between, greaterThanOrEqual, lessThanOrEqual, greaterThan, lessThan);
        }
    }

}
