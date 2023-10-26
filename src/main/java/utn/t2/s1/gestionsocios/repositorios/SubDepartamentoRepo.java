package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.SubDepartamento;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.List;

public interface SubDepartamentoRepo extends JpaRepository<SubDepartamento, Long> {

    SubDepartamento findByIdAndEstado(Long id, Estado estado);
//    SubDepartamento findByNombreAndEstado(String nombre,Estado estado);

    List<SubDepartamento> findAllByEstado(Estado estado);

    Page<SubDepartamento> findAllByDepartamentoIdAndEstado(Pageable page, Long departamentoId, Estado estado);

//    Page<SubDepartamento> findAllByDepartamentoAndEstado(Pageable page, Long departamentoId, Estado estado);
    Page<SubDepartamento> findByNombreSubDepartamentoContainsAndEstado(Pageable page, String nombre, Estado estado);

}
