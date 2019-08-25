package l.y.z.mybatis.extension.example.select;

import l.y.z.mybatis.extension.BaseEntity;
import l.y.z.mybatis.extension.example.OrderBy;
import l.y.z.mybatis.extension.example.Where;
import lombok.Getter;
import lombok.ToString;

/**
 * liuyazong
 * 2019/8/25 21:02
 * <p></p>
 */
@Getter
@ToString
public class SelectExample<T extends BaseEntity> {

    private Select select;
    private From from;
    private Where where;
    private OrderBy orderby;

    private SelectExample(Select select, From from, Where where, OrderBy orderby) {
        this.select = select;
        this.from = from;
        this.where = where;
        this.orderby = orderby;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Getter
    @ToString
    public static class Builder<T extends BaseEntity> {

        private Select select;
        private From from;
        private Where where;
        private OrderBy orderby;

        public Builder<T> select(Select select) {
            this.select = select;
            return this;
        }

        public Builder<T> from(From from) {
            this.from = from;
            return this;
        }

        public Builder<T> where(Where where) {
            this.where = where;
            return this;
        }

        public Builder<T> orderby(OrderBy orderby) {
            this.orderby = orderby;
            return this;
        }

        public SelectExample<T> build() {
            return new SelectExample<>(select, from, where, orderby);
        }
    }
}
