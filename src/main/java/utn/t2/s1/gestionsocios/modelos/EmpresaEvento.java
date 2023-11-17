package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

@Entity
@Data
@Table(name="empresa_evento")
public class EmpresaEvento extends Persistente {
    @OneToOne
    private Socio socio;
    @Column(name = "otraEmpresa")
    private String otraEmpresa;
}
