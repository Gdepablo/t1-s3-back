package utn.t2.s1.gestionsocios.modelos;


import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.EstadoEvento;
import utn.t2.s1.gestionsocios.persistencia.Modalidad;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="evento")
public class Evento extends Persistente {
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "fecha_de_inicio", nullable = false)
    private Date fechaInicio;
    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;

    @OneToOne(cascade = CascadeType.ALL)
    private Lugar lugar;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "modalidad", nullable = false)
    private Modalidad modalidad;

    @Column(name = "direccion", nullable = false)
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Participante> participantes;
    @Column(name = "direccion", nullable = false)
    private EstadoEvento estadoEvento;
    @Column(name = "link_descripcion", nullable = false)
    private String linkInscripcion;

}
