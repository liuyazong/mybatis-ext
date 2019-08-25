package l.y.z.mybatis.extension.example;

import l.y.z.mybatis.extension.BaseEntity;
import l.y.z.mybatis.extension.example.delete.DeleteExample;
import l.y.z.mybatis.extension.example.insert.InsertExample;
import l.y.z.mybatis.extension.example.select.SelectExample;
import l.y.z.mybatis.extension.example.update.UpdateExample;

/**
 * liuyazong
 * 2019/8/25 21:02
 * <p></p>
 */
public interface Example {

    static <ID, T extends BaseEntity<ID>> InsertExample.Builder<ID, T> insert() {
        return new InsertExample.Builder<>();
    }

    static <T extends BaseEntity> DeleteExample.Builder<T> delete() {
        return new DeleteExample.Builder<>();
    }

    static <T extends BaseEntity> UpdateExample.Builder<T> update() {
        return new UpdateExample.Builder<>();
    }

    static <T extends BaseEntity> SelectExample.Builder<T> select() {
        return new SelectExample.Builder<>();
    }
}

