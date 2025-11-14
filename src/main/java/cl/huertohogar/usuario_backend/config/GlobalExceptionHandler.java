package cl.huertohogar.usuario_backend.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.huertohogar.usuario_backend.exception.AuthenticationFailedException;
import cl.huertohogar.usuario_backend.exception.CiudadNotFoundException;
import cl.huertohogar.usuario_backend.exception.CiudadNotValidException;
import cl.huertohogar.usuario_backend.exception.EmailAlreadyExistsException;
import cl.huertohogar.usuario_backend.exception.RegionNotFoundException;
import cl.huertohogar.usuario_backend.exception.RegionNotValidException;
import cl.huertohogar.usuario_backend.exception.UsuarioNotFoundException;
import cl.huertohogar.usuario_backend.exception.UsuarioNotValidException;
import io.swagger.v3.oas.annotations.Hidden;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    private Map<String, Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("message", message);
        errorResponse.put("status", status.value());
        return errorResponse;
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNotFound(UsuarioNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(UsuarioNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNotValid(UsuarioNotValidException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CiudadNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCiudadNotFound(CiudadNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(CiudadNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleCiudadNotValid(CiudadNotValidException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(RegionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRegionNotFound(RegionNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(RegionNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleRegionNotValid(RegionNotValidException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationFailed(AuthenticationFailedException ex) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(buildErrorResponse("Error interno del servidor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
