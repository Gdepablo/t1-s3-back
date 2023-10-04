package utn.t2.s1.gestionsocios;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import utn.t2.s1.gestionsocios.configs.FileStorageProperties;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Socios API", version = "1.0", description = "..."))
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class GestionSociosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionSociosApplication.class, args);
	}

}
