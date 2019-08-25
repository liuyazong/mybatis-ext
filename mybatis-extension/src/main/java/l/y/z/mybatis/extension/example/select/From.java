package l.y.z.mybatis.extension.example.select;

import l.y.z.mybatis.extension.BaseEntity;
import lombok.Getter;
import lombok.ToString;

/**
 * liuyazong
 * 2019/8/25 21:02
 * <p></p>
 */
@Getter
@ToString
public class From {
    private Class<? extends BaseEntity> entityClass;

    private From(Class<? extends BaseEntity> entityClass) {
        this.entityClass = entityClass;
    }


    public static From.Builder newBuilder() {
        return new From.Builder();
    }

    public static class Builder {
        private Class<? extends BaseEntity> entityClass;

        public From.Builder tableOf(Class<? extends BaseEntity> entityClass) {
            this.entityClass = entityClass;
            return this;
        }

        public From build() {
            return new From(this.entityClass);
        }
    }
}