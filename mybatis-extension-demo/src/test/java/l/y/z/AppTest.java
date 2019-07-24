package l.y.z;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import l.y.z.mybatis.extension.demo.entity.User;
import l.y.z.mybatis.extension.demo.mapper.UserMapper;
import l.y.z.mybatis.extension.provider.SelectProvider;
import l.y.z.mybatis.extension.query.OrderBy;
import l.y.z.mybatis.extension.query.Query;
import lombok.extern.slf4j.Slf4j;
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

    private static SqlSessionFactory sqlSessionFactory;

    static {
        //datasource
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://localhost:3306/dev?useUnicode=true&autoReconnect=true&characterEncoding=utf8&serverTimezone=UTC";
        String username = "root";
        String password = "mysql";
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(200);
        hikariConfig.setMinimumIdle(8);

        DataSource dataSource = //new PooledDataSource(driverClassName, jdbcUrl, username, password);
                new HikariDataSource(hikariConfig);

        Environment env = new Environment(UUID.randomUUID().toString(), new ManagedTransactionFactory(), dataSource);

        //configuration
        Configuration configuration = new Configuration();
        configuration.setUseGeneratedKeys(true);
        configuration.setDefaultExecutorType(ExecutorType.REUSE);
        configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.WARNING);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(Slf4jImpl.class);
        configuration.setEnvironment(env);

        //page
        configuration.addInterceptor(new PageInterceptor());

        //mapper
        configuration.addMapper(UserMapper.class);

        //sqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        log.debug("static");
    }

    private SqlSession sqlSession;

    @Before
    public void before() {
        sqlSession = sqlSessionFactory.openSession();
        log.debug("before");
    }

    @Test
    public void insert() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User info = new User();
        info.setUserName(UUID.randomUUID().toString());
        info.setPassword(UUID.randomUUID().toString());
        info.setSalt(UUID.randomUUID().toString());
        Integer insert = mapper.insert(info);
        log.debug("insert {}, result {}", info, insert);
    }

    @Test
    public void delete() {
        User info = new User();
        info.setId(2);
        info.setUserName("哈哈哈哈哈哈");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Integer delete = mapper.delete(info);
        log.debug("delete {}, result {}", info, delete);
    }

    @Test
    public void update() {
        User info = new User();
        info.setId(2);
        info.setUserName("哈哈哈哈哈哈");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Integer update = mapper.update(info);
        log.debug("update {}, result {}", info, update);
    }

    @Test
    public void select() {
        User info = new User();
        info.setId(2);
        info.setUserName("哈哈哈哈哈哈");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.select(info);
        log.debug("select {}, result {}", info, users);
    }

    @After
    public void after() {
        sqlSession.close();
        log.debug("after");
    }

    @Test
    public void test() {
        SelectProvider selectProvider = new SelectProvider();
        String username = selectProvider.camelCaseToUnderscore("userName");
        log.debug(username);
    }

    @Test
    public void page() {
        User info = new User();
        info.setPassword("a");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        PageInfo<User> pageInfo = mapper.paged(info, new Query(new OrderBy[]{new OrderBy("createTime", false), new OrderBy("id", true)},
                1, 10));

        log.debug("pageInfo {}", pageInfo);

        pageInfo.getList().forEach(e -> {
            log.debug("{}", e);
        });
    }

}
