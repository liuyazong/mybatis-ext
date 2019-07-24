package l.y.z.mybatis.extension.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import l.y.z.mybatis.extension.provider.SQLProvider;
import l.y.z.mybatis.extension.query.OrderBy;
import l.y.z.mybatis.extension.query.Query;

/**
 * 分页
 *
 * @param <T> 数据库表对应的实体类
 */
public interface PagedMapper<T> extends SelectMapper<T>, SQLProvider {

    /**
     * 分页，query中有分页和排序属性
     *
     * @param t     数据库表对应的实体对象
     * @param query 分页和排序
     * @return 分页数据
     */
    default PageInfo<T> paged(T t, Query query) {
        Page<T> page = PageHelper.<T>startPage(query.getPageNum(), query.getPageSize());
        StringBuilder sb = new StringBuilder();
        // 排序
        OrderBy[] orderBies = query.getOrderBies();
        int length = orderBies.length;
        if (null != orderBies && length > 0) {
            for (int i = 0; i < length; i++) {
                OrderBy orderBY = orderBies[i];
                sb.append(this.camelCaseToUnderscore(orderBY.getField())).append(" ").append(orderBY.getAscending() ? "asc" : "desc");
                if (i != length - 1) {
                    sb.append(", ");
                }
            }
        }
        page.setOrderBy(sb.toString());
        // 查询
        select(t);
        return page.toPageInfo();
    }
}
