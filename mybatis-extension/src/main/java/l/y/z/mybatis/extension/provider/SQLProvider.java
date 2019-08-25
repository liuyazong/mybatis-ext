package l.y.z.mybatis.extension.provider;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import l.y.z.mybatis.extension.Strings;
import l.y.z.mybatis.extension.example.Where;
import l.y.z.mybatis.extension.example.delete.DeleteExample;
import l.y.z.mybatis.extension.example.insert.InsertExample;
import l.y.z.mybatis.extension.example.select.SelectExample;
import l.y.z.mybatis.extension.example.update.UpdateExample;
import l.y.z.mybatis.extension.query.OrderByPair;
import l.y.z.mybatis.extension.query.Query;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * liuyazong
 * 2019/8/25 21:06
 * <p></p>
 */
public class SQLProvider implements ProviderMethodResolver {

    /**
     * 驼峰式转下划线。如输入 userName 则输出 user_name
     *
     * @param source 源字符串，可能是驼峰式
     * @return 下划线字符串
     */
    private String camelCaseToUnderscore(String source) {
        return Strings.camelCaseToUnderscore(source);
    }

    /**
     * 仅处理t中非null属性和其对应的表字段
     *
     * @param t 输入对象
     * @return sql
     */
    public String insert(Object t) throws IllegalAccessException {
        SQL sql = new SQL();
        Class<?> aClass = t.getClass();
        String tn = this.camelCaseToUnderscore(aClass.getSimpleName());
        sql.INSERT_INTO(tn);
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            String fn = field.getName();
            String cn = this.camelCaseToUnderscore(fn);
            field.setAccessible(true);
            Object value = field.get(t);
            if (null != value) {
                sql.VALUES(cn, "#{" + fn + "}");
            }
        }
        return sql.toString();
    }

    /**
     * 仅处理t中非null的id属性
     *
     * @param t 输入对象
     * @return sql
     */
    public String delete(Object t) throws NoSuchFieldException {
        SQL sql = new SQL();
        Class<?> aClass = t.getClass();
        String tn = this.camelCaseToUnderscore(aClass.getSimpleName());
        sql.DELETE_FROM(tn);
        Field field = aClass.getDeclaredField("id");
        String fn = field.getName();
        String cn = this.camelCaseToUnderscore(fn);
        field.setAccessible(true);
        sql.WHERE(cn + " = #{" + fn + "}");
        return sql.toString();
    }

    /**
     * 仅处理t中非null属性和其对应的表字段，并且t中必须有非null的id属性
     *
     * @param t 输入对象
     * @return sql
     */
    public String update(Object t) throws IllegalAccessException, NoSuchFieldException {
        SQL sql = new SQL();
        Class<?> aClass = t.getClass();
        String tn = this.camelCaseToUnderscore(aClass.getSimpleName());
        sql.UPDATE(tn);
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            String fn = field.getName();
            String cn = this.camelCaseToUnderscore(fn);
            field.setAccessible(true);
            Object value = field.get(t);
            if (null != value) {
                sql.SET(cn + " = #{" + fn + "}");
            }
        }
        // id
        Field field = aClass.getDeclaredField("id");
        field.setAccessible(true);
        Object value = field.get(t);
        if (null != value) {
            String fn = field.getName();
            String cn = this.camelCaseToUnderscore(fn);
            sql.WHERE(cn + " = #{" + fn + "}");
        }
        return sql.toString();
    }

    /**
     * 仅处理t中非null属性和其对应的表字段。
     * 如果T是<code>l.y.z.mybatis.extension.query.Query</code>的子类，则支持分页及排序
     *
     * @param t 输入对象
     * @return sql
     */
    public String select(Object t) throws IllegalAccessException {
        SQL sql = new SQL();
        Class<?> aClass = t.getClass();
        String tn = this.camelCaseToUnderscore(aClass.getSimpleName());
        sql.FROM(tn);
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            String fn = field.getName();
            String cn = this.camelCaseToUnderscore(fn);
            sql.SELECT(cn);
            field.setAccessible(true);
            Object value = field.get(t);
            if (null != value) {
                sql.WHERE(cn + " = #{" + fn + "}");
            }
        }
        Page<Object> page = PageHelper.getLocalPage();
        if (null == page && t instanceof Query) {
            Query query = (Query) t;
            // 排序
            OrderByPair[] orderByPairs = query.getOrderByPairs();
            int length;
            if (null != orderByPairs && (length = orderByPairs.length) > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    OrderByPair orderByPair = orderByPairs[i];
                    sb.append(this.camelCaseToUnderscore(orderByPair.getField())).append(" ").append(orderByPair.isAscending() ? "asc" : "desc");
                    if (i != length - 1) {
                        sb.append(", ");
                    }
                }
                sql.ORDER_BY(sb.toString());

            } else {
                String orderBy = query.getOrderBy();
                if (null != orderBy) {
                    sql.ORDER_BY(orderBy);
                }
            }
            // 分页
            Integer pageNum = query.getPageNum();
            Integer pageSize = query.getPageSize();
            if (null != pageNum && null != pageSize) {
                sql.OFFSET((pageNum - 1) * pageSize).LIMIT(pageSize);
            }
        }
        return sql.toString();
    }

    public String insertByExample(InsertExample example) {
        SQL sql = new SQL().INSERT_INTO(camelCaseToUnderscore(example.getInto().getEntityClass().getSimpleName()));
        Optional.ofNullable(example.getInsert())
                .ifPresent(insert ->
                        Optional.ofNullable(insert.getFields())
                                .ifPresent(fields ->
                                        fields.forEach((key, value) ->
                                                sql.VALUES(camelCaseToUnderscore(key), "#{insert.fields." + key + "}")
                                        )
                                )

                );
        return sql.toString();
    }

    public String deleteByExample(DeleteExample example) {
        SQL sql = new SQL().DELETE_FROM(camelCaseToUnderscore(example.getTable().getEntityClass().getSimpleName()));
        //where
        where(sql, example.getWhere());
        return sql.toString();
    }

    public String updateByExample(UpdateExample example) {
        SQL sql = new SQL().UPDATE(camelCaseToUnderscore(example.getTable().getEntityClass().getSimpleName()));
        Optional.ofNullable(example.getUpdate())
                .ifPresent(update ->
                        Optional.ofNullable(update.getFields())
                                .ifPresent(fields ->
                                        fields.forEach((key, value) ->
                                                sql.SET(camelCaseToUnderscore(key) + " = #{update.fields." + key + "}")
                                        )
                                )

                );
        //where
        where(sql, example.getWhere());
        return sql.toString();
    }

    public String selectByExample(SelectExample example) {
        SQL sql = new SQL();
        //select
        Optional.ofNullable(example.getSelect())
                .ifPresent(select ->
                        Optional.ofNullable(select.getFields())
                                .ifPresent(fields -> {
                                            for (String field : fields) {
                                                sql.SELECT(camelCaseToUnderscore(field));
                                            }
                                        }
                                )
                );
        //from
        Optional.ofNullable(example.getFrom())
                .ifPresent(from ->
                        sql.FROM(camelCaseToUnderscore(from.getEntityClass().getSimpleName()))
                );
        //where
        where(sql, example.getWhere());
        // order by
        Optional.ofNullable(example.getOrderby())
                .ifPresent(orderby ->
                        Optional.ofNullable(orderby.getOrderBy())
                                .ifPresent(ob ->
                                        ob.forEach((key, value) -> {
                                                    sql.ORDER_BY(camelCaseToUnderscore(key) + " " + (value ? "desc" : "asc"));
                                                }
                                        )
                                )
                );
        return sql.toString();
    }

    private void where(SQL sql, Where where2) {
        Optional.of(where2)
                .ifPresent(where -> {
                    // =
                    Optional.ofNullable(where.getEqual())
                            .ifPresent(eq ->
                                    eq.forEach((key, value) -> {
                                                if (value.isAndOr()) {
                                                    sql.AND().WHERE(camelCaseToUnderscore(key) + " = #{where.equal." + key + ".object}");
                                                } else {
                                                    sql.OR().WHERE(camelCaseToUnderscore(key) + " = #{where.equal." + key + ".object}");
                                                }

                                            }
                                    )
                            );
                    // !=
                    Optional.ofNullable(where.getNotEqual())
                            .ifPresent(neq ->
                                    neq.forEach((key, value) -> {
                                                if (value.isAndOr()) {
                                                    sql.AND().WHERE(camelCaseToUnderscore(key) + " != #{where.notEqual." + key + ".object}");
                                                } else {
                                                    sql.OR().WHERE(camelCaseToUnderscore(key) + " != #{where.notEqual." + key + ".object}");
                                                }
                                            }
                                    )
                            );
                    // in
                    Optional.ofNullable(where.getIn())
                            .ifPresent(in ->
                                    in.forEach((key, value) -> {
                                                StringBuilder sb = new StringBuilder(" IN (");
                                                Object[] objects = value.getObject();
                                                for (int i = 0; i < objects.length; i++) {
                                                    sb.append("#{where.in.").append(key).append(".object[").append(i).append("]}");
                                                    if (i != objects.length - 1) {
                                                        sb.append(", ");
                                                    }
                                                }
                                                sb.append(")");
                                                if (value.isAndOr()) {
                                                    sql.AND().WHERE(camelCaseToUnderscore(key) + sb.toString());
                                                } else {
                                                    sql.OR().WHERE(camelCaseToUnderscore(key) + sb.toString());
                                                }
                                            }
                                    )
                            );
                    // not in
                    Optional.ofNullable(where.getNotIn())
                            .ifPresent(notIn ->
                                    notIn.forEach((key, value) -> {
                                                StringBuilder sb = new StringBuilder(" NOT").append(" IN (");
                                                Object[] objects = value.getObject();
                                                for (int i = 0; i < objects.length; i++) {
                                                    sb.append("#{where.in.").append(key).append(".object[").append(i).append("]}");
                                                    if (i != objects.length - 1) {
                                                        sb.append(", ");
                                                    }
                                                }
                                                sb.append(")");
                                                if (value.isAndOr()) {
                                                    sql.AND().WHERE(camelCaseToUnderscore(key) + sb.toString());
                                                } else {
                                                    sql.OR().WHERE(camelCaseToUnderscore(key) + sb.toString());
                                                }
                                            }
                                    )
                            );
                    // between
                    Optional.ofNullable(where.getBetween())
                            .ifPresent(between ->
                                    between.forEach((key, value) -> {
                                                if (value.isAndOr()) {
                                                    sql.AND().WHERE(camelCaseToUnderscore(key) + " BETWEEN #{where.between." + key + ".object[0]} AND #{where.between." + key + ".object[1]}");
                                                } else {
                                                    sql.OR().WHERE(camelCaseToUnderscore(key) + " BETWEEN #{where.between." + key + ".object[0]} AND #{where.between." + key + ".object[1]}");
                                                }
                                            }
                                    )
                            );
                    // >=
                    Optional.ofNullable(where.getGreaterThanOrEqual())
                            .ifPresent(gte ->
                                    gte.forEach((key, value) -> {
                                                if (value.isAndOr()) {
                                                    sql.AND().WHERE(camelCaseToUnderscore(key) + " >= #{where.greaterThanOrEqual." + key + ".object}");
                                                } else {
                                                    sql.OR().WHERE(camelCaseToUnderscore(key) + " >= #{where.greaterThanOrEqual." + key + ".object}");
                                                }
                                            }
                                    )
                            );
                    // <=
                    Optional.ofNullable(where.getLessThanOrEqual())
                            .ifPresent(lte ->
                                    lte.forEach((key, value) -> {
                                                if (value.isAndOr()) {
                                                    sql.AND().WHERE(camelCaseToUnderscore(key) + " <= #{where.lessThanOrEqual." + key + ".object}");
                                                } else {
                                                    sql.OR().WHERE(camelCaseToUnderscore(key) + " <= #{where.lessThanOrEqual." + key + ".object}");
                                                }
                                            }
                                    )
                            );
                    // >
                    Optional.ofNullable(where.getGreaterThan())
                            .ifPresent(gt ->
                                    gt.forEach((key, value) -> {
                                                if (value.isAndOr()) {
                                                    sql.AND().WHERE(camelCaseToUnderscore(key) + " > #{where.greaterThan." + key + ".object}");
                                                } else {
                                                    sql.OR().WHERE(camelCaseToUnderscore(key) + " > #{where.greaterThan." + key + ".object}");
                                                }
                                            }
                                    )
                            );
                    // <
                    Optional.ofNullable(where.getLessThan())
                            .ifPresent(lt ->
                                    lt.forEach((key, value) -> {
                                                if (value.isAndOr()) {
                                                    sql.AND().WHERE(camelCaseToUnderscore(key) + " < #{where.lessThan." + key + ".object}");
                                                } else {
                                                    sql.OR().WHERE(camelCaseToUnderscore(key) + " < #{where.lessThan." + key + ".object}");
                                                }
                                            }
                                    )
                            );
                });
    }
}
