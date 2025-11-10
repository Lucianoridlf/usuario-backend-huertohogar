package cl.huertohogar.usuario_backend.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orden")
@Schema(description = "Entidad que representa una orden de compra realizada por un usuario")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden", nullable = false)
    @Schema(description = "Identificador único de la orden", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idOrden;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @Schema(description = "Usuario que realiza la orden", required = true, implementation = Usuario.class)
    private Usuario usuario;

    @Column(name= "fecha_orden", nullable = false)
    @Schema(description = "Fecha en que se realizó la orden", example = "2025-11-10", required = true, format = "date")
    private LocalDate fechaOrden;

    @Column(name= "estado", nullable = false)
    @Schema(description = "Estado actual de la orden", example = "PENDIENTE", required = true, allowableValues = {"PENDIENTE", "PROCESANDO", "ENVIADA", "ENTREGADA", "CANCELADA"})
    private String estado;

    @Column(name= "total_orden", nullable = false)
    @Schema(description = "Total de la orden en pesos chilenos", example = "45990.50", required = true, minimum = "0")
    private Double totalOrden;

    @Column(name= "direccion_envio", nullable = false)
    @Schema(description = "Dirección de envío de la orden", example = "Av. Providencia 456, Santiago", required = true, maxLength = 200)
    private String direccionEnvio;

}