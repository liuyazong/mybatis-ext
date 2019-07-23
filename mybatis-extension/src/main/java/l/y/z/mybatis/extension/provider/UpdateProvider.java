package l.y.z.mybatis.extension.provider;

import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;

public class UpdateProvider implements SQLProvider {

    /**
     * 仅处理t中非null属性和其对应的表字段，并且t中必须有非null的id属性
     *
     * @param t 输入对象
     * @return sql
     */
    public String update(Object t) throws IllegalAccessException {
        SQL sql = new SQL();
        Class<?> aClass = t.getClass();
        String tn = this.camelCaseToUnderscore(aClass.getSimpleName());
        sql.UPDATE(tn);
        Field[] fields = aClass.getDeclaredFields();
        boolean hasId = false;
        for (Field field : fields) {
            String fn = field.getName();
            String cn = this.camelCaseToUnderscore(fn);
            field.setAccessible(true);
            Object value = field.get(t);
            if (null != value) {
                sql.SET(cn + " = #{" + fn + "}");
                if ("id".equals(cn)) {
                    hasId = true;
                    sql.WHERE(cn + " = #{" + fn + "}");
                }
            }
        }
        if (!hasId) {
            throw new RuntimeException(String.format("类%s没有id属性，或者其id属性值为null", aClass.getName()));
        }
        return sql.toString();
    }
}
