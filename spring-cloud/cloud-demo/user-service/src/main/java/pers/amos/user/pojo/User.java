package pers.amos.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author amos
 * 与你同在
 * @create 2019/5/19 - 20:28
 */

@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String username;

    private String password;

    private String name;

    private Integer age;

    private Integer sex;

    private Date birthday;

    private Date created;

    private Date updated;

    private String note;

}
