package cl.huertohogar.usuario_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para autenticación de usuario")
public class AuthenticationRequest {

    @Schema(description = "ID del usuario", example = "1")
    private Integer idUsuario;

    @Schema(description = "Contraseña del usuario", example = "MiPassword123!")
    private String password;

}
