package l.y.z.mybatis.extension.provider;

import l.y.z.mybatis.extension.utils.Strings;

public interface SQLProvider {

    /**
     * 驼峰式转下划线。如输入 userName 则输出 user_name
     *
     * @param source 源字符串，可能是驼峰式
     * @return 下划线字符串
     */
    default String camelCaseToUnderscore(String source) {
        return Strings.camelCaseToUnderscore(source);
    }
}
