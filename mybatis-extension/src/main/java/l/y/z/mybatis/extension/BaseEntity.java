package l.y.z.mybatis.extension;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * liuyazong
 * 2019/8/25 21:06
 * <p></p>
 */
@Data
public abstract class BaseEntity<ID> {
    private ID id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
