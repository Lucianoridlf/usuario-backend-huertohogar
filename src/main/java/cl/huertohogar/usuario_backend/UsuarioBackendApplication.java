package cl.huertohogar.usuario_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import cl.huertohogar.usuario_backend.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class UsuarioBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioBackendApplication.class, args);
	}

}
