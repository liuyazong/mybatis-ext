package l.y.z.mybatis.extension.mapper;

import l.y.z.mybatis.extension.provider.SQLProvider;
import org.apache.ibatis.annotations.DeleteProvider;

/**
 * liuyazong
 * 2019/8/25 21:04
 * <p>删除</p>
 *
 * @param <T> 数据库表对应的实体类
 */
public interface DeleteMapper<T> {

    /**
     * 根据id删除，t中必须有id属性且非null
     *
     * @param t 数据库表对应的实体对象
     * @return 删除的行数
     */
    @DeleteProvider(type = SQLProvider.class, method = "delete")
    Integer delete(T t);
}
