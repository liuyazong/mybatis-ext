package l.y.z.mybatis.extension.mapper;

import org.apache.ibatis.annotations.UpdateProvider;

/**
 * 更新
 *
 * @param <T> 数据库表对应的实体类
 */
public interface UpdateMapper<T> {

    /**
     * 根据id更新非null的属性，t中必须有id属性且非null
     *
     * @param t 数据库表对应的实体对象
     * @return 匹配的行数
     */
    @UpdateProvider(type = l.y.z.mybatis.extension.provider.UpdateProvider.class, method = "update")
    Integer update(T t);
}
