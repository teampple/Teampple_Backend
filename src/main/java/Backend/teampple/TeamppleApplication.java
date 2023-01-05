package Backend.teampple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //  AuditingEntityListener 사용하기 위해 필요
public class TeamppleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamppleApplication.class, args);
	}

}
