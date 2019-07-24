package l.y.z.mybatis.extension.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String userName;
    private String password;
    private String salt;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
