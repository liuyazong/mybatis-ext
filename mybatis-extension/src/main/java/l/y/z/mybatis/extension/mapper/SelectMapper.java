package l.y.z.mybatis.extension.mapper;

import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface SelectMapper<T> {

    /**
     * 根据非null属性查询
     *
     * @param t 输入
     * @return 结果集合
     */
    @SelectProvider(type = l.y.z.mybatis.extension.provider.SelectProvider.class, method = "select")
    List<T> select(T t);
}
