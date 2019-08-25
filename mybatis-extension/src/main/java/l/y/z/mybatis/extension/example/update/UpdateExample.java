package l.y.z.mybatis.extension.example.update;

import l.y.z.mybatis.extension.BaseEntity;
import l.y.z.mybatis.extension.example.Table;
import l.y.z.mybatis.extension.example.Where;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateExample<T extends BaseEntity> {

    private Update update;
    private Table table;
    private Where where;

    private UpdateExample(Update update, Table table, Where where) {
        this.update = update;
        this.table = table;
        this.where = where;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder<T extends BaseEntity> {

        private Update update;
        private Table table;
        private Where where;

        public Builder<T> update(Update update) {
            this.update = update;
            return this;
        }

        public Builder<T> table(Table table) {
            this.table = table;
            return this;
        }

        public Builder<T> where(Where where) {
            this.where = where;
            return this;
        }

        public UpdateExample<T> build() {
            return new UpdateExample<>(update, table, where);
        }
    }
}
