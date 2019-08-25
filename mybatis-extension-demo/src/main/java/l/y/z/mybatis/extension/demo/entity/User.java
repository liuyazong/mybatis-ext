package l.y.z.mybatis.extension.demo.entity;

import l.y.z.mybatis.extension.BaseEntity;
import lombok.*;

import java.math.BigInteger;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class User extends BaseEntity<BigInteger> {
    private String userName;
    private String password;
    private String salt;
}
