package cl.huertohogar.usuario_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para actualizar contraseña de forma segura")
public class PasswordUpdateRequest {

    @Schema(description = "Contraseña anterior del usuario", example = "OldPassword123!")
    private String oldPassword;

    @Schema(description = "Nueva contraseña del usuario", example = "NewPassword456!")
    private String newPassword;

}
