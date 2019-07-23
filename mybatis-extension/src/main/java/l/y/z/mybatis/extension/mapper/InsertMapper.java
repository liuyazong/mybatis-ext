package l.y.z.mybatis.extension.mapper;

import org.apache.ibatis.annotations.InsertProvider;

public interface InsertMapper<T> {

    /**
     * 根据id更新非null的属性，t中必须有id属性且非null
     *
     * @param t 输入
     * @return 更新的行数
     */
    @InsertProvider(type = l.y.z.mybatis.extension.provider.InsertProvider.class, method = "insert")
    Integer insert(T t);
}
