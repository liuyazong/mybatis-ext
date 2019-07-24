package l.y.z.mybatis.extension.provider;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;

public class DeleteProvider implements SQLProvider, ProviderMethodResolver {

    public DeleteProvider() {
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
}
