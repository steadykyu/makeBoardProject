package steadykyu.kyumarket.entity.user;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode // equals 와 hashcode() override
@NoArgsConstructor
@AllArgsConstructor
@ToString
@IdClass(UsersId.class) // UsersId라는 식별자 클래스에 @Id 속성이 다 있어야 한다.
public class Users implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;
    @Id
    @Column(name = "NAME")
    private String name;

    private int age;
}
