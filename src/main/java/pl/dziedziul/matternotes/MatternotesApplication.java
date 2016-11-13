package pl.dziedziul.matternotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import pl.dziedziul.matternotes.config.JpaAuditingConfig;

@SpringBootApplication
@Import(JpaAuditingConfig.class)
public class MatternotesApplication {
	public static void main(String[] args) {
		SpringApplication.run(MatternotesApplication.class, args);
	}
}
