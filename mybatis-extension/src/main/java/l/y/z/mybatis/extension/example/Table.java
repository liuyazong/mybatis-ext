package l.y.z.mybatis.extension.example;

import l.y.z.mybatis.extension.BaseEntity;
import lombok.Getter;
import lombok.ToString;

/**
 * liuyazong
 * 2019/8/25 21:03
 * <p></p>
 */
@Getter
@ToString
public class Table {
    private Class<? extends BaseEntity> entityClass;

    private Table(Class<? extends BaseEntity> entityClass) {
        this.entityClass = entityClass;
    }

    public static Table.Builder newBuilder() {
        return new Table.Builder();
    }

    public static class Builder {
        private Class<? extends BaseEntity> entityClass;

        public Table.Builder tableOf(Class<? extends BaseEntity> entityClass) {
            this.entityClass = entityClass;
            return this;
        }

        public Table build() {
            return new Table(this.entityClass);
        }
    }
}