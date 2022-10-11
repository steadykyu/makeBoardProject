package steadykyu.kyumarket.entity.user;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersId implements Serializable {
    private Long id; // Users Class의 필드 이름이 꼭 같아야 한다.
    private String name; // Users Class의 필드 이름이 꼭 같아야 한다.
}
