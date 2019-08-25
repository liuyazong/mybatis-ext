package l.y.z.mybatis.extension.mapper;

import l.y.z.mybatis.extension.BaseEntity;

/**
 * liuyazong
 * 2019/8/25 21:03
 * <p>结合增、删、改、查和分页</p>
 *
 * @param <ID> 数据库表对应的主键
 * @param <T>  数据库表对应的实体类
 */
public interface BaseMapper<ID, T extends BaseEntity<ID>> extends
        InsertMapper<T>,
        UpdateMapper<T>,
        SelectMapper<T>,
        DeleteMapper<T>,
        PagedMapper<T>,
        ExampleMapper<ID, T> {
}
