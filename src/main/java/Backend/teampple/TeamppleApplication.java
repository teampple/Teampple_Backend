package Backend.teampple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing /**AuditingEntityListener*/
@EnableAsync
public class TeamppleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamppleApplication.class, args);
	}

}
