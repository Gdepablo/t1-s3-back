package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

@Entity
@Data
@Table(name = "autoridad_departamento")
public class AutoridadDepartamento extends Persistente {


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;


}

