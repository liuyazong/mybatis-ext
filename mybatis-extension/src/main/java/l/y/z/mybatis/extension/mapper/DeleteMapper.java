package l.y.z.mybatis.extension.mapper;

import org.apache.ibatis.annotations.DeleteProvider;

/**
 * 删除
 * @param <T> 数据库表对应的实体类
 */
public interface DeleteMapper<T> {

    /**
     * 根据id删除，t中必须有id属性且非null
     *
     * @param t 数据库表对应的实体对象
     * @return 删除的行数
     */
    @DeleteProvider(type = l.y.z.mybatis.extension.provider.DeleteProvider.class, method = "delete")
    Integer delete(T t);
}
