package l.y.z.mybatis.extension.mapper;

import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 查询
 * @param <T> 数据库表对应的实体类
 */
public interface SelectMapper<T> {

    /**
     * 根据非null属性查询
     *
     * @param t 数据库表对应的实体对象
     * @return 结果集合
     */
    @SelectProvider(type = l.y.z.mybatis.extension.provider.SelectProvider.class, method = "select")
    List<T> select(T t);
}
