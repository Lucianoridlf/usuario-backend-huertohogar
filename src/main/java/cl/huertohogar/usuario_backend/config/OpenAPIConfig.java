package cl.huertohogar.usuario_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
            .info(new Info()
                .title("API de Usuarios - Huerto Hogar")
                .description("API REST para gestión completa de usuarios, ciudades, regiones y autenticación con JWT. " +
                            "Incluye endpoints protegidos con control de acceso basado en roles (RBAC). " +
                            "\n\n**Roles disponibles:** USER, ADMIN" +
                            "\n\n**Autenticación:** JWT Bearer Token" +
                            "\n\n**Para usar endpoints protegidos:**" +
                            "\n1. Autentícate en POST /api/v1/usuarios/authenticate" +
                            "\n2. Copia el token JWT recibido" +
                            "\n3. Click en 'Authorize' arriba y pega el token" +
                            "\n4. Ahora puedes usar todos los endpoints según tu rol")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Equipo Huerto Hogar")
                    .email("contacto@huertohogar.cl")
                    .url("https://huertohogar.cl"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
            .addServersItem(new Server()
                .url("http://localhost:8080")
                .description("Servidor Local - Desarrollo"))
            .addServersItem(new Server()
                .url("https://api.huertohogar.cl")
                .description("Servidor Producción - DigitalOcean"))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Ingresa el token JWT obtenido del endpoint /api/v1/usuarios/authenticate")));
    }
}
