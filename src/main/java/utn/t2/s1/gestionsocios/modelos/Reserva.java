package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "reserva")
public class Reserva extends Persistente {

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "duracion")
    private Float duracion;

    @Column(name = "codigo_de_seguimiento", unique = true)
    @GeneratedValue( generator = "uuid"  )
    @GenericGenerator( name = "uuid", strategy = "uuid2")
    private String codigoDeSeguimiento;


    @ManyToMany
    @JoinTable(name = "recursos_reserva",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "recurso_id"))
    private Set<Recurso> recursos;

    @ManyToOne
    @JoinColumn(name = "espacio_fisico_id")
    private EspacioFisico espacioFisico;

    @ManyToOne
    @JoinColumn(name = "estado_reserva_id")
    private EstadoReserva estadoReserva;

    @OneToOne
    private Encargado encargado;

}
