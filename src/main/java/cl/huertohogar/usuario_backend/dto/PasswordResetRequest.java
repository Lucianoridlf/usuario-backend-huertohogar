package cl.huertohogar.usuario_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para resetear contraseña")
public class PasswordResetRequest {

    @Schema(description = "Nueva contraseña", example = "ResetPassword123!")
    private String newPassword;

}
