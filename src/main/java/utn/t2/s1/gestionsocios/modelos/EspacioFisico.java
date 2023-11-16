package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.util.List;

@Entity
@Data
@Table(name="espacio_fisico")
public class EspacioFisico extends Persistente {

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "espacioFisico")
    private List<Reserva> reservas;

}
