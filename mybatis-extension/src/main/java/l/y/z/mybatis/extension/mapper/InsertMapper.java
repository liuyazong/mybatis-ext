package l.y.z.mybatis.extension.mapper;

import l.y.z.mybatis.extension.provider.SQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

/**
 * liuyazong
 * 2019/8/25 21:04
 * <p>插入</p>
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
    @InsertProvider(type = SQLProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    Integer insert(T t);
}
