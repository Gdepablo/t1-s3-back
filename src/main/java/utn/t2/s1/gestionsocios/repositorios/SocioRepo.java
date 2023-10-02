package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.persistencia.Estado;

public interface SocioRepo extends JpaRepository<Socio, Long> {

    public Socio findByIdAndEstado(Long id, Estado estado);
    public Page<Socio> findAllByEstado(Pageable pageable, Estado estado);

}
