package l.y.z.mybatis.extension.example.insert;

import l.y.z.mybatis.extension.BaseEntity;
import lombok.Getter;
import lombok.ToString;

/**
 * liuyazong
 * 2019/8/25 21:02
 * <p></p>
 */
@ToString
@Getter
public class Into {
    private Class<? extends BaseEntity> entityClass;

    private Into(Class<? extends BaseEntity> entityClass) {
        this.entityClass = entityClass;
    }


    public static Into.Builder newBuilder() {
        return new Into.Builder();
    }

    public static class Builder {
        private Class<? extends BaseEntity> entityClass;

        public Into.Builder tableOf(Class<? extends BaseEntity> entityClass) {
            this.entityClass = entityClass;
            return this;
        }

        public Into build() {
            return new Into(this.entityClass);
        }
    }
}
