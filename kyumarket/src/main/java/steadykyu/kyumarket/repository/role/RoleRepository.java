package steadykyu.kyumarket.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import steadykyu.kyumarket.entity.member.Role;
import steadykyu.kyumarket.entity.member.RoleType;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}