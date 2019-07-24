package l.y.z.mybatis.extension.provider;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;

public class UpdateProvider implements SQLProvider, ProviderMethodResolver {

    public UpdateProvider() {

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
}
