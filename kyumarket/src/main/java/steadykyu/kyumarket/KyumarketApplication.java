package steadykyu.kyumarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import steadykyu.kyumarket.entity.member.Role;
import steadykyu.kyumarket.entity.member.RoleType;
import steadykyu.kyumarket.repository.member.MemberRepository;
import steadykyu.kyumarket.repository.role.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@EnableJpaAuditing
@SpringBootApplication
public class KyumarketApplication {
	public static void main(String[] args) {SpringApplication.run(KyumarketApplication.class, args);}

}
