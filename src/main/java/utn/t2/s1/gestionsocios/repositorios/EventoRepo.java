package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Evento;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.List;
import java.util.Optional;

public interface EventoRepo extends JpaRepository<Evento,Long> {
    Optional<Evento> findByIdAndEstado(Long id, Estado estado);
    Page<Evento> findByNombreContainsAndEstado(Pageable pageable,String nombre, Estado estado);

    Page<Evento> findAllByEstado(Pageable pageable, Estado estado);
}
