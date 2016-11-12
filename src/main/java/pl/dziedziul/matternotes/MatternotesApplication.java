package pl.dziedziul.matternotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MatternotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatternotesApplication.class, args);
	}
}
