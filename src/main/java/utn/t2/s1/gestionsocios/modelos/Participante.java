package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

@Entity
@Data
@Table(name="participante")
public class Participante extends Persistente {
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "mail", nullable = false)
    private String mail;
    @Column(name = "referente", nullable = false)
    private String referente;
    @OneToOne
    private EmpresaEvento empresaEvento;
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
}
