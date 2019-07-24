package l.y.z.mybatis.extension.provider;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import l.y.z.mybatis.extension.query.OrderByPair;
import l.y.z.mybatis.extension.query.Query;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;

public class SelectProvider implements SQLProvider, ProviderMethodResolver {

    public SelectProvider() {
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
}
