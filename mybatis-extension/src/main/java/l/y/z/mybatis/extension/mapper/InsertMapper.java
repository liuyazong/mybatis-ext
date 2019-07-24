package l.y.z.mybatis.extension.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

/**
 * 插入
 *
 * @param <T> 数据库表对应的实体类
 */
public interface InsertMapper<T> {

    /**
     * 插入非空属性
     *
     * @param t 数据库表对应的实体对象
     * @return 更新的行数
     */
    @InsertProvider(type = l.y.z.mybatis.extension.provider.InsertProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    Integer insert(T t);
}
