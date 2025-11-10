package cl.huertohogar.usuario_backend.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "region")
@Schema(description = "Entidad que representa una región geográfica del país")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region", nullable = false)
    @Schema(description = "Identificador único de la región", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idRegion;

    @Column(name = "nombre_region", nullable = false, unique = true)
    @Schema(description = "Nombre de la región", example = "Región Metropolitana", required = true, minLength = 3, maxLength = 100)
    private String nombreRegion;

}
