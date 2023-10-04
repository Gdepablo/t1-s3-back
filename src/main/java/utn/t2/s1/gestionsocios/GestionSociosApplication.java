package utn.t2.s1.gestionsocios;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import utn.t2.s1.gestionsocios.configs.FileStorageProperties;

import java.util.Arrays;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Socios API", version = "1.0", description = "..."))
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class GestionSociosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionSociosApplication.class, args);
	}
	@Bean
	public MappingJackson2HttpMessageConverter octetStreamJsonConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "octet-stream")));
		return converter;
	}

}
