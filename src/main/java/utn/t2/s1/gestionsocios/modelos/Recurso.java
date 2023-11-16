package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.util.Set;


@Entity
@Data
@Table(name="recursos")
public class Recurso extends Persistente {

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "recursos")
    private Set<Reserva> reserva;


}
