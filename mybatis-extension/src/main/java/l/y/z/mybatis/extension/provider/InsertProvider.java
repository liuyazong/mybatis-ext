package l.y.z.mybatis.extension.provider;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;

public class InsertProvider implements SQLProvider, ProviderMethodResolver {

    public InsertProvider() {
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
}
