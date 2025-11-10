package cl.huertohogar.usuario_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response de autenticación exitosa con token JWT y datos del usuario")
public class AuthenticationResponse {
    
    @Schema(description = "Token JWT para autenticar siguientes peticiones", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "ID del usuario autenticado", example = "1")
    private Integer idUsuario;
    
    @Schema(description = "Email del usuario", example = "luis@example.com")
    private String email;
    
    @Schema(description = "Primer nombre del usuario", example = "Luis")
    private String pNombre;
    
    @Schema(description = "Apellido paterno del usuario", example = "González")
    private String aPaterno;
    
    @Schema(description = "Rol del usuario", example = "ADMIN", allowableValues = {"USER", "ADMIN"})
    private String rol;
}