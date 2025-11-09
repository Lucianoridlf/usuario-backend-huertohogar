package cl.huertohogar.usuario_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.huertohogar.usuario_backend.dto.AuthenticationRequest;
import cl.huertohogar.usuario_backend.dto.PasswordResetRequest;
import cl.huertohogar.usuario_backend.dto.PasswordUpdateRequest;
import cl.huertohogar.usuario_backend.dto.PasswordValidationRequest;
import cl.huertohogar.usuario_backend.model.Password;
import cl.huertohogar.usuario_backend.service.PasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/passwords")
@Tag(name = "API Password", description = "Operaciones relacionadas con las contraseñas de usuarios")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @Operation(
        summary = "Crear una nueva contraseña",
        description = "Crea una nueva contraseña cifrada para un usuario. La contraseña debe cumplir con los requisitos de seguridad."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Contraseña creada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Password.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos ingresados NO válidos",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\":\"2025-11-08T10:30:00\",\"message\":\"La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial (@$!%*?&)\",\"status\":400}")
            )
        )
    })
    @PostMapping("")
    public ResponseEntity<Password> createPassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Ingrese datos de la contraseña a crear",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = Password.class),
                    examples = @ExampleObject(
                        name = "Ejemplo de contraseña",
                        value = "{\"idPassword\":0,\"idUsuario\":1,\"password\":\"MiPassword123!\"}"
                    )
                )
            )
            @org.springframework.web.bind.annotation.RequestBody Password password) { 
        Password nuevaPassword = passwordService.save(password);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPassword);
    }

    @Operation(
        summary = "Verificar si existe contraseña para un usuario",
        description = "Verifica si un usuario tiene una contraseña registrada sin exponer información sensible"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Verificación completada exitosamente"
        )
    })
    @GetMapping("/usuario/{idUsuario}/existe")
    public ResponseEntity<Boolean> existePasswordParaUsuario(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Integer idUsuario) {
        return ResponseEntity.ok(passwordService.existsByIdUsuario(idUsuario));
    }

    @Operation(
        summary = "Actualizar contraseña",
        description = "Actualiza la contraseña de un usuario. Requiere la contraseña anterior para validación."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contraseña actualizada exitosamente"),
        @ApiResponse(
            responseCode = "404",
            description = "Contraseña no encontrada",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos o contraseña anterior incorrecta",
            content = @Content(mediaType = "application/json")
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePassword(
            @Parameter(description = "ID de la contraseña a actualizar", example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Contraseñas antigua y nueva",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = PasswordUpdateRequest.class),
                    examples = @ExampleObject(
                        value = "{\"oldPassword\":\"OldPassword123!\",\"newPassword\":\"NewPassword456!\"}"
                    )
                )
            )
            @org.springframework.web.bind.annotation.RequestBody PasswordUpdateRequest request) {
        passwordService.update(id, request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok("Contraseña actualizada exitosamente");
    }

    @Operation(
        summary = "Resetear contraseña",
        description = "Resetea la contraseña sin validar la anterior (solo para administradores)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contraseña reseteada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Contraseña no encontrada"),
        @ApiResponse(responseCode = "400", description = "Nueva contraseña inválida")
    })
    @PatchMapping("/{id}/reset")
    public ResponseEntity<String> resetPassword(
            @Parameter(description = "ID de la contraseña", example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nueva contraseña",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = PasswordResetRequest.class),
                    examples = @ExampleObject(
                        value = "{\"newPassword\":\"ResetPassword123!\"}"
                    )
                )
            )
            @org.springframework.web.bind.annotation.RequestBody PasswordResetRequest request) {
        passwordService.resetPassword(id, request.getNewPassword());
        return ResponseEntity.ok("Contraseña reseteada exitosamente");
    }

    @Operation(
        summary = "Eliminar contraseña",
        description = "Elimina permanentemente una contraseña del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contraseña eliminada exitosamente"),
        @ApiResponse(
            responseCode = "404",
            description = "Contraseña no encontrada",
            content = @Content(mediaType = "application/json")
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassword(
            @Parameter(description = "ID de la contraseña a eliminar", example = "1")
            @PathVariable Integer id) {
        passwordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Autenticar usuario",
        description = "Valida las credenciales de un usuario verificando su contraseña"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
        @ApiResponse(
            responseCode = "401",
            description = "Credenciales inválidas",
            content = @Content(mediaType = "application/json")
        )
    })
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Credenciales del usuario",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = AuthenticationRequest.class),
                    examples = @ExampleObject(
                        value = "{\"idUsuario\":1,\"password\":\"MiPassword123!\"}"
                    )
                )
            )
            @org.springframework.web.bind.annotation.RequestBody AuthenticationRequest request) {
        boolean isAuthenticated = passwordService.authenticate(request.getIdUsuario(), request.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Autenticación exitosa");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    @Operation(
        summary = "Validar formato de contraseña",
        description = "Verifica si una contraseña cumple con los requisitos de seguridad"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Validación completada")
    })
    @PostMapping("/validate")
    public ResponseEntity<String> validatePassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Contraseña a validar",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = PasswordValidationRequest.class),
                    examples = @ExampleObject(
                        value = "{\"password\":\"TestPassword123!\"}"
                    )
                )
            )
            @org.springframework.web.bind.annotation.RequestBody PasswordValidationRequest request) {
        boolean isValid = passwordService.isValidPassword(request.getPassword());
        String strength = passwordService.getPasswordStrength(request.getPassword());
        return ResponseEntity.ok("Válida: " + isValid + ", Fortaleza: " + strength);
    }

}
