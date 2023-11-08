package utn.t2.s1.gestionsocios.modelos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaFin;

    @OneToOne(cascade = CascadeType.ALL)
    private Lugar lugar;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "modalidad", nullable = false)
    private Modalidad modalidad;

    @Column(name = "direccion", nullable = false)
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Participante> participantes;
    @Column(name = "direccion", nullable = false)
    private EstadoEvento estadoEvento;
    @Column(name = "link_descripcion", nullable = false)
    private String linkInscripcion;



}




