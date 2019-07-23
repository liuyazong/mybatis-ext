package l.y.z.mybatis.extension.mapper;

import org.apache.ibatis.annotations.UpdateProvider;

public interface UpdateMapper<T> {

    /**
     * 根据id更新非null的属性，t中必须有id属性且非null
     *
     * @param t 输入
     * @return 更新的行数
     */
    @UpdateProvider(type = l.y.z.mybatis.extension.provider.UpdateProvider.class, method = "update")
    Integer update(T t);
}
