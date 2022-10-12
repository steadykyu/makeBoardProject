package steadykyu.kyumarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KyumarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(KyumarketApplication.class, args);
	}

}
