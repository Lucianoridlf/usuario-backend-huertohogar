package cl.huertohogar.usuario_backend.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;
    
    @Column(name = "pnombre", nullable = false)
    private String pNombre;
    
    @Column(name = "snombre", nullable = false)
    private String sNombre;    

    @Column(name= "apaterno", nullable = false)
    private String aPaterno;

    @Column(name= "amaterno", nullable = false)
    private String aMaterno;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @OneToOne
    @JoinColumn(name = "password", nullable = false)
    private Password password;  //falta añadir la clase Password
}
