package pl.dziedziul.matternotes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@Profile("!webMvcTest") //hack to make @WebMvcTest happy
public class JpaAuditingConfig {
}
