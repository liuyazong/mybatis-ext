package l.y.z.mybatis.extension.provider;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

public interface SQLProvider extends ProviderMethodResolver {

    /**
     * 驼峰式转下划线。如输入 UserInfo 则输出 user_info
     *
     * @param source 源字符串，可能是驼峰式
     * @return 下划线字符串
     */
    default String camelCaseToUnderscore(String source) {
        char[] chars = source.toCharArray();
        StringBuffer tn = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 65 && chars[i] <= 90) {
                if (0 == i) {
                    tn.append((char) (chars[i] + 32));
                } else {
                    tn.append('_').append((char) (chars[i] + 32));
                }
            } else {
                tn.append(chars[i]);
            }
        }
        return tn.toString().toLowerCase();
    }
}
