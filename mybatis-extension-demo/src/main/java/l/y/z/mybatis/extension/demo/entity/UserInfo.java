package l.y.z.mybatis.extension.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfo {
    private Integer id;
    private String name;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
