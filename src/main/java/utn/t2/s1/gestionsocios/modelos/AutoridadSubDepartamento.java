package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

@Entity
@Data
@Table(name = "autoridad_sub_departamento")
public class AutoridadSubDepartamento extends Persistente {


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "subdepartamento_id")
    private SubDepartamento subDepartamento;


}

