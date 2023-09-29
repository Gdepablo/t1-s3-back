package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.List;

public interface SocioRepo extends JpaRepository<Socio, Long> {

    public Socio findByIdAndEstado(Long id, Estado estado);
    public Page<Socio> findAllByEstado(Pageable pageable, Estado estado);

}
