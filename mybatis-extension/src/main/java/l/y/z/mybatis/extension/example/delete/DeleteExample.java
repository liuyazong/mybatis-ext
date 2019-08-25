package l.y.z.mybatis.extension.example.delete;

import l.y.z.mybatis.extension.BaseEntity;
import l.y.z.mybatis.extension.example.Table;
import l.y.z.mybatis.extension.example.Where;
import lombok.Getter;
import lombok.ToString;

/**
 * liuyazong
 * 2019/8/25 21:01
 * <p></p>
 */
@ToString
@Getter
public class DeleteExample<T extends BaseEntity> {

    private Table table;
    private Where where;

    private DeleteExample(Table table, Where where) {
        this.table = table;
        this.where = where;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder<T extends BaseEntity> {

        private Table table;
        private Where where;

        public Builder<T> table(Table table) {
            this.table = table;
            return this;
        }

        public Builder<T> where(Where where) {
            this.where = where;
            return this;
        }

        public DeleteExample<T> build() {
            return new DeleteExample<>(table, where);
        }
    }
}
