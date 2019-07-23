package l.y.z.mybatis.extension.provider;

import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;

public class SelectProvider implements SQLProvider {

    /**
     * 仅处理t中非null属性和其对应的表字段
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
        return sql.toString();
    }
}
