package l.y.z.mybatis.extension.mapper;

import l.y.z.mybatis.extension.BaseEntity;
import l.y.z.mybatis.extension.example.delete.DeleteExample;
import l.y.z.mybatis.extension.example.insert.InsertExample;
import l.y.z.mybatis.extension.example.select.SelectExample;
import l.y.z.mybatis.extension.example.update.UpdateExample;
import l.y.z.mybatis.extension.provider.SQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * liuyazong
 * 2019/8/25 21:04
 * <p></p>
 */
public interface ExampleMapper<ID, T extends BaseEntity<ID>> {

    @InsertProvider(type = SQLProvider.class, method = "insertByExample")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    Integer insertByExample(InsertExample<ID, T> example);

    @DeleteProvider(type = SQLProvider.class, method = "deleteByExample")
    Integer deleteByExample(DeleteExample<T> example);

    @UpdateProvider(type = SQLProvider.class, method = "updateByExample")
    Integer updateByExample(UpdateExample<T> example);

    @SelectProvider(type = SQLProvider.class, method = "selectByExample")
    List<T> selectByExample(SelectExample<T> example);

}
