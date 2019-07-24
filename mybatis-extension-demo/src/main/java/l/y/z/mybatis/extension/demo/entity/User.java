package l.y.z.mybatis.extension.demo.entity;

import l.y.z.mybatis.extension.query.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class User extends Query {
    private Integer id;
    private String userName;
    private String password;
    private String salt;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
