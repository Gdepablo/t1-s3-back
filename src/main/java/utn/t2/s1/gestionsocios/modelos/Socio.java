package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="socio")
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="denominacion")
    private String denominacion;
    private String telefono;
    private TipoSocio tipo;
    private String mail;
    private String descripcion;
    private String web;
    private LocalDateTime fechaAlta;
    private String logo;
    @ManyToOne
    private Categoria categoria;

}

