package cl.huertohogar.usuario_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para validar contraseña")
public class PasswordValidationRequest {

    @Schema(description = "Contraseña a validar", example = "TestPassword123!")
    private String password;

}
