package utn.t2.s1.gestionsocios.modelos;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.util.List;

@Data
@Entity
@Table(name = "sub_departamento")
public class SubDepartamento extends Persistente {

    @Column(name = "nombre", nullable = false)
    private String nombreSubDepartamento;
    @Column(name = "objetivo", nullable = false)
    private String objetivo;



    @OneToMany(mappedBy = "subDepartamento", cascade = CascadeType.ALL)
    private List<AutoridadSubDepartamento> autoridadSubDepartamentos;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;


}
