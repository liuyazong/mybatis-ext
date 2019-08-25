package l.y.z.mybatis.extension.mapper;

import l.y.z.mybatis.extension.provider.SQLProvider;
import org.apache.ibatis.annotations.UpdateProvider;


/**
 * liuyazong
 * 2019/8/25 21:06
 * <p>更新</p>
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
    @UpdateProvider(type = SQLProvider.class, method = "update")
    Integer update(T t);
}
