package cl.huertohogar.usuario_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "Identificador único del usuario", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idUsuario;
    
    @Column(name = "nombre", nullable = false)
    @Schema(description = "Nombre del usuario", example = "Luis")
    private String nombre;
    
    @Column(name = "snombre", nullable = true)
    @Schema(description = "Segundo nombre del usuario (opcional)", example = "Andrés")
    private String sNombre;

    @Column(name = "apaterno", nullable = false)
    @Schema(description = "Apellido paterno del usuario", example = "González")
    private String aPaterno;

    @Column(name = "amaterno", nullable = false)
    @Schema(description = "Apellido materno del usuario", example = "Ramírez")
    private String aMaterno;

    @Column(name = "rut", nullable = false, unique = true, length = 8)
    @Schema(description = "RUT del usuario sin puntos ni guión", example = "12345678")
    private String rut;

    @Column(name = "dv", nullable = false, length = 1)
    @Schema(description = "Dígito verificador del RUT", example = "9")
    private String dv;

    @Column(name = "fecha_nacimiento", nullable = false)
    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-05-15")
    private LocalDate fechaNacimiento;

    @Column(name = "id_region", nullable = false)
    @Schema(description = "ID de la región (llave foránea)", example = "13")
    private Integer idRegion;

    @Column(name = "direccion", nullable = false)
    @Schema(description = "Dirección del usuario", example = "Av. Libertador 123, Santiago")
    private String direccion;

    @Column(name = "email", nullable = false, unique = true)
    @Schema(description = "Correo electrónico del usuario", example = "luisgonzalez@gmail.com")
    private String email;

    @Column(name = "telefono", nullable = true)
    @Schema(description = "Número de teléfono del usuario (opcional)", example = "+56987654321")
    private String telefono;

    @Column(name = "password_hashed", nullable = false)
    @Schema(description = "Contraseña del usuario", example = "MiPassword123!", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String passwordHashed;

    @Column(name = "rol", nullable = false)
    @Schema(description = "Rol del usuario: USER, ADMIN", example = "USER", accessMode = Schema.AccessMode.READ_ONLY)
    private String rol = "USER";

    // Getters y Setters con @JsonProperty para mapeo correcto
    
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @JsonProperty("nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @JsonProperty("sNombre")
    public String getSNombre() {
        return sNombre;
    }

    public void setSNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    @JsonProperty("aPaterno")  // ✅ Mapea "aPaterno" del JSON a este getter
    public String getAPaterno() {
        return aPaterno;
    }

    public void setAPaterno(String aPaterno) {
        this.aPaterno = aPaterno;
    }

    @JsonProperty("aMaterno")  // ✅ Mapea "aMaterno" del JSON a este getter
    public String getAMaterno() {
        return aMaterno;
    }

    public void setAMaterno(String aMaterno) {
        this.aMaterno = aMaterno;
    }

    @JsonProperty("rut")
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    @JsonProperty("dv")
    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    @JsonProperty("fechaNacimiento")
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @JsonProperty("idRegion")
    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    @JsonProperty("direccion")
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("telefono")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @JsonProperty(value = "passwordHashed", access = JsonProperty.Access.WRITE_ONLY)  // ✅ Solo escritura
    public String getPasswordHashed() {
        return passwordHashed;
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // ✅ Solo lectura
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
