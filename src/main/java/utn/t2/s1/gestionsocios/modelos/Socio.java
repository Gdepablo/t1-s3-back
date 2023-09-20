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
    @Column(name="telefono")
    private String telefono;
    private TipoSocio tipo;
    @Column(name="mail")
    private String mail;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="web")
    private String web;
    private LocalDateTime fechaAlta;
    @Column(name="logo")
    private String logo;
    @ManyToOne
    private Categoria categoria;

}

