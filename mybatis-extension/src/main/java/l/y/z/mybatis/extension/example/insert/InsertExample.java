package l.y.z.mybatis.extension.example.insert;

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
public class InsertExample<ID, T extends BaseEntity<ID>> {
    private ID id;
    private Insert insert;
    private Into into;

    private InsertExample(Insert insert, Into into) {
        this.insert = insert;
        this.into = into;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void setId(ID id) {
        this.id = id;
    }

    public static class Builder<ID, T extends BaseEntity<ID>> {
        private Insert insert;
        private Into into;

        public Builder<ID, T> insert(Insert insert) {
            this.insert = insert;
            return this;
        }

        public Builder<ID, T> into(Into into) {
            this.into = into;
            return this;
        }

        public InsertExample<ID, T> build() {
            return new InsertExample<>(insert, into);
        }
    }
}
