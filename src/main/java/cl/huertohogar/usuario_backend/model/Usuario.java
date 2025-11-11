package cl.huertohogar.usuario_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un usuario del sistema con autenticación y roles")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "Identificador único del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idUsuario;
    
    @Column(name = "pnombre", nullable = false)
    @JsonProperty("pNombre")
    @Schema(description = "Primer nombre del usuario", example = "Luis", required = true, minLength = 2, maxLength = 50)
    private String pNombre;
    
    @Column(name = "snombre", nullable = false)
    @JsonProperty("sNombre")
    @Schema(description = "Segundo nombre del usuario", example = "Andrés", required = true, minLength = 2, maxLength = 50)
    private String sNombre;

    @Column(name = "apaterno", nullable = false)
    @JsonProperty("aPaterno")
    @Schema(description = "Apellido paterno del usuario", example = "González", required = true, minLength = 2, maxLength = 50)
    private String aPaterno;

    @Column(name = "amaterno", nullable = false)
    @JsonProperty("aMaterno")
    @Schema(description = "Apellido materno del usuario", example = "Ramírez", required = true, minLength = 2, maxLength = 50)
    private String aMaterno;

    @Column(name = "email", nullable = false, unique = true)
    @Schema(description = "Correo electrónico del usuario (único)", example = "luis.gonzalez@example.com", required = true, format = "email")
    private String email;

    @Column(name = "telefono", nullable = false)
    @Schema(description = "Número de teléfono del usuario", example = "+56987654321", required = true, pattern = "^\\+?[0-9]{8,15}$")
    private String telefono;

    @Column(name = "direccion", nullable = false)
    @Schema(description = "Dirección del usuario", example = "Av. Libertador Bernardo O'Higgins 123, Santiago", required = true, maxLength = 200)
    private String direccion;

    @Column(name = "password_hashed", nullable = false)
    @JsonProperty("passwordHashed")
    @Schema(description = "Contraseña del usuario (se hashea automáticamente con BCrypt)", example = "MiPassword123!", required = true, minLength = 8, accessMode = Schema.AccessMode.WRITE_ONLY)
    private String passwordHashed;

    @Column(name = "rol", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // ✅ Solo lectura, no se puede enviar en POST
    @Schema(description = "Rol del usuario: USER, ADMIN", example = "USER", accessMode = Schema.AccessMode.READ_ONLY)
    private String rol = "USER"; // ✅ Por defecto siempre USER

}
