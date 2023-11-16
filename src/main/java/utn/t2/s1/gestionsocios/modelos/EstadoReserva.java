package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.util.List;

@Entity
@Data
@Table(name="estado_reserva")
public class EstadoReserva extends Persistente {

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "estadoReserva")
    private List<Reserva> reservas;

}
