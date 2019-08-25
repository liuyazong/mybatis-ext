package l.y.z.mybatis.extension.demo.mapper;

import l.y.z.mybatis.extension.demo.entity.User;
import l.y.z.mybatis.extension.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<BigInteger, User> {

    @Select("select * from user where id = #{id}")
    List<User> sss(Map<String, Object> map);
}
