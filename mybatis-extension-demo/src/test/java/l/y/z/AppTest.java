package l.y.z;

import l.y.z.mybatis.extension.demo.entity.UserInfo;
import l.y.z.mybatis.extension.demo.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Slf4j
public class AppTest {

    SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() {
        Configuration configuration = new Configuration();
        configuration.setUseGeneratedKeys(true);
        configuration.setDefaultExecutorType(ExecutorType.REUSE);
        configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.WARNING);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(Slf4jImpl.class);

        DataSource dataSource = new PooledDataSource("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/dev?useUnicode=true&autoReconnect=true&characterEncoding=utf8&serverTimezone=UTC", "root", "mysql");
        Environment env = new Environment(UUID.randomUUID().toString(), new ManagedTransactionFactory(), dataSource);
        configuration.setEnvironment(env);

        configuration.addMapper(UserInfoMapper.class);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }


    @Test
    public void insert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        UserInfo info = new UserInfo();
        info.setName(UUID.randomUUID().toString());
        info.setPassword(UUID.randomUUID().toString());
        Integer insert = mapper.insert(info);
        log.debug("insert {}, result {}", info, insert);
        sqlSession.close();
    }

    @Test
    public void update() {
        UserInfo info = new UserInfo();
        info.setId(2);
        info.setName(UUID.randomUUID().toString());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        Integer update = mapper.update(info);
        log.debug("update {}, result {}", info, update);
    }

    @Test
    public void select() {
        UserInfo info = new UserInfo();
        info.setId(1);
        info.setName("哈哈哈哈哈哈");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        List<UserInfo> userInfos = mapper.select(info);
        log.debug("select {}, result {}", info, userInfos);
    }

    @After
    public void after() {
    }

}
