package l.y.z.mybatis.extension.mapper;

import l.y.z.mybatis.extension.provider.SQLProvider;
import l.y.z.mybatis.extension.query.Query;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * liuyazong
 * 2019/8/25 21:05
 * <p>查询，支持分页及排序</p>
 *
 * @param <T> 数据库表对应的实体类
 */
public interface SelectMapper<T> {

    /**
     * 根据非null属性查询。
     * 如果T是<code>l.y.z.mybatis.extension.query.Query</code>的子类，则支持分页及排序
     *
     * @param t 数据库表对应的实体对象
     * @return 结果集合
     * @see Query
     */
    @SelectProvider(type = SQLProvider.class, method = "select")
    List<T> select(T t);
}
