package steadykyu.kyumarket.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import steadykyu.kyumarket.entity.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email); // 1
    Optional<Member> findByNickname(String nickname); // 2

    boolean existsByEmail(String email); // 3
    boolean existsByNickname(String nickname); // 4
}

