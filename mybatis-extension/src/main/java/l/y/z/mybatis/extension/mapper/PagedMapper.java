package l.y.z.mybatis.extension.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import l.y.z.mybatis.extension.Strings;
import l.y.z.mybatis.extension.query.OrderByPair;
import l.y.z.mybatis.extension.query.Query;

/**
 * liuyazong
 * 2019/8/25 21:05
 * <p>分页，基于PageHelper</p>
 *
 * @param <T> 数据库表对应的实体类
 */
public interface PagedMapper<T> extends SelectMapper<T> {

    /**
     * 分页，query中有分页和排序属性
     *
     * @param t     数据库表对应的实体对象
     * @param query 分页和排序
     * @return 分页数据
     */
    default PageInfo<T> paged(T t, Query query) {
        Page<T> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        // 排序
        OrderByPair[] orderByPairs = query.getOrderByPairs();
        int length;
        if (null != orderByPairs && (length = orderByPairs.length) > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                OrderByPair orderByPair = orderByPairs[i];
                sb.append(Strings.camelCaseToUnderscore(orderByPair.getField())).append(" ").append(orderByPair.isAscending() ? "asc" : "desc");
                if (i != length - 1) {
                    sb.append(", ");
                }
            }
            page.setOrderBy(sb.toString());
        } else {
            String orderBy = query.getOrderBy();
            if (null != orderBy) {
                page.setOrderBy(orderBy);
            }
        }
        // 查询
        select(t);
        return page.toPageInfo();
    }
}
