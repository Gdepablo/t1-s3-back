package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Departamento;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.Optional;

public interface DepartamentoRepo extends JpaRepository<Departamento, Long> {
    Optional<Departamento> findByIdAndEstado(Long id, Estado estado);

}
