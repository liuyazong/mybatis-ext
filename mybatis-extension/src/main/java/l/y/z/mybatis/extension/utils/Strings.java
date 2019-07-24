package l.y.z.mybatis.extension.utils;

/**
 * 字符串工具类
 */
public abstract class Strings {
    /**
     * 驼峰式转下划线。如输入 userName 则输出 user_name、输入 User 输出 user
     *
     * @param source 源字符串，可能是驼峰式
     * @return 下划线字符串
     */
    public static String camelCaseToUnderscore(String source) {
        char[] chars = source.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 65 && chars[i] <= 90) {
                if (0 == i) {
                    sb.append((char) (chars[i] + 32));
                } else {
                    sb.append('_').append((char) (chars[i] + 32));
                }
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.toString().toLowerCase();
    }
}
