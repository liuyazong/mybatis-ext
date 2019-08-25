package l.y.z;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import l.y.z.mybatis.extension.demo.entity.User;
import l.y.z.mybatis.extension.demo.mapper.UserMapper;
import l.y.z.mybatis.extension.example.Example;
import l.y.z.mybatis.extension.example.OrderBy;
import l.y.z.mybatis.extension.example.Table;
import l.y.z.mybatis.extension.example.Where;
import l.y.z.mybatis.extension.example.delete.DeleteExample;
import l.y.z.mybatis.extension.example.insert.Insert;
import l.y.z.mybatis.extension.example.insert.InsertExample;
import l.y.z.mybatis.extension.example.insert.Into;
import l.y.z.mybatis.extension.example.select.From;
import l.y.z.mybatis.extension.example.select.Select;
import l.y.z.mybatis.extension.example.select.SelectExample;
import l.y.z.mybatis.extension.example.update.Update;
import l.y.z.mybatis.extension.example.update.UpdateExample;
import l.y.z.mybatis.extension.provider.SQLProvider;
import l.y.z.mybatis.extension.query.OrderByPair;
import l.y.z.mybatis.extension.query.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Slf4j
public class AppTest {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        //datasource
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/dev?useUnicode=true&autoReconnect=true&characterEncoding=utf8&serverTimezone=UTC";
        String username = "dev";
        String password = "Dev%^78";
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
        configuration.setProxyFactory(new CglibProxyFactory());

       /* configuration.getTypeHandlerRegistry()
                .register(new BaseTypeHandler<Map>() {
                    @Override
                    public void setNonNullParameter(PreparedStatement ps, int i, Map parameter, JdbcType jdbcType) throws SQLException {
                        ps.set
                        log.info("");
                    }

                    @Override
                    public Map getNullableResult(ResultSet rs, String columnName) throws SQLException {
                        log.info("");
                        return rs.getObject(columnName, Map.class);
                    }

                    @Override
                    public Map getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
                        log.info("");
                        return rs.getObject(columnIndex, Map.class);
                    }

                    @Override
                    public Map getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
                        log.info("");
                        return null;
                    }
                });*/
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
    public void byExample() {
        SQLProvider sqlProvider = new SQLProvider();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        SelectExample<User> userSelectExample = Example.<User>select()
                .select(Select.newBuilder().field("*").build())
                .from(From.newBuilder().tableOf(User.class).build())
                .where(Where.newBuilder().equal("id", 4L).build())
                .orderby(OrderBy.newBuilder().orderBy("id", true).build())
                .build();
        log.info("select {}", mapper.selectByExample(userSelectExample));
        log.info("{}", sqlProvider.selectByExample(userSelectExample));

        InsertExample<BigInteger, User> userInsertExample = Example.<BigInteger, User>insert()
                .insert(Insert.newBuilder()
                        .field("userName", UUID.randomUUID().toString())
                        .field("password", UUID.randomUUID().toString())
                        .field("salt", UUID.randomUUID().toString())
                        .build())
                .into(Into.newBuilder().tableOf(User.class).build())
                .build();
        Integer integer = mapper.insertByExample(userInsertExample);
        Long id = userInsertExample.getId().longValue();
        log.info("{},insert {}", id, integer);
        log.info("{}", sqlProvider.insertByExample(userInsertExample));

        UpdateExample<User> userUpdateExample = Example.<User>update()
                .update(Update.newBuilder()
                        .field("userName", "1")
                        .field("password", "1")
                        .build())
                .table(Table.newBuilder().tableOf(User.class).build())
                .where(Where.newBuilder().equal("id", 4L).build()).build();
        log.info("update {}", mapper.updateByExample(userUpdateExample));
        log.info("{}", sqlProvider.updateByExample(userUpdateExample));

        DeleteExample<User> userDeleteExample = Example.<User>delete()
                .table(Table.newBuilder().tableOf(User.class).build())
                .where(Where.newBuilder().equal("id", 4L).build()).build();
        log.info("delete {}", mapper.deleteByExample(userDeleteExample));
        log.info("{}", sqlProvider.deleteByExample(userDeleteExample));

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
        info.setId(BigInteger.valueOf(2L));
        info.setUserName("哈哈哈哈哈哈");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Integer delete = mapper.delete(info);
        log.debug("delete {}, result {}", info, delete);
    }

    @Test
    public void update() {
        User info = new User();
        info.setId(BigInteger.valueOf(2L));
        info.setUserName("哈哈哈哈哈哈");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Integer update = mapper.update(info);
        log.debug("update {}, result {}", info, update);
    }

    @Test
    public void select() {
//        User info = new User();
////        info.setId(2);
////        info.setUserName("哈哈哈哈哈哈");
//        info.setOrderByPairs(new OrderByPair[]{new OrderByPair("create_time", false)});
//        info.setOrderBy("id desc");
//        info.setPageNum(1);
//        info.setPageSize(10);
//        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        List<User> users = mapper.select(info);
//        log.debug("select {}, result {}, {}", info, users, mapper.getClass());
    }

    @After
    public void after() {
        sqlSession.close();
        log.debug("after");
    }

    @Test
    public void test() {

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        SelectExample<User> userSelectExample =
                Example.<User>select()
                        .select(Select.newBuilder().field("*").build())
                        .from(From.newBuilder().tableOf(User.class).build())
                        .where(Where.newBuilder().equal("id", 1L).build())
                        .orderby(OrderBy.newBuilder().orderBy("id", true).build())
                        .build();

        List<User> users =
                mapper.selectByExample(userSelectExample);
        log.debug(" result {} ", users);

        /*Integer integer = mapper.insertByExample(Example.newBuilder()
                .from(From.newBuilder().from(User.class))
                .insert(
                        Insert.newBuilder()
                                .field("userName", UUID.randomUUID().toString())
                                .field("password", UUID.randomUUID().toString())
                                .field("salt", UUID.randomUUID().toString())
                ).build());
        log.info("insert: {}", integer);*/
    }

    @Test
    public void page() {
        User info = new User();
        info.setUserName("哈哈哈哈哈哈");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        PageInfo<User> pageInfo = mapper.paged(info, new Query(new OrderByPair[]{new OrderByPair("createTime", false), new OrderByPair("id", true)},
                1, 10) {
        });

        log.debug("pageInfo {}", pageInfo);

        pageInfo.getList().forEach(e -> log.debug("{}", e));
    }

}
