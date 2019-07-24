package l.y.z.mybatis.extension.mapper;

/**
 * 结合增、删、改、查和分页
 *
 * @param <T> 数据库表对应的实体类
 */
public interface BaseMapper<T> extends
        InsertMapper<T>,
        UpdateMapper<T>,
        SelectMapper<T>,
        DeleteMapper<T>,
        PagedMapper<T> {
}
