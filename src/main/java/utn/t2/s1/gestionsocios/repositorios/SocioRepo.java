package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import utn.t2.s1.gestionsocios.persistencia.Estado;

public interface SocioRepo extends JpaRepository<Socio, Long> {

    public Socio findByIdAndEstado(Long id, Estado estado);
    public Page<Socio> findAllByEstado(Pageable pageable, Estado estado);
    public Page<Socio> findByDenominacionContainingAndTipo_NombreAndTipo_EstadoAndEstado(Pageable pageable, String denominacion, String tipo, Estado estadoTipo ,Estado estado);
    public Page<Socio> findByTipo_NombreAndTipo_EstadoAndEstado(Pageable pageable, String tipo, Estado estadoTipo, Estado estado);
    public Page<Socio> findByDenominacionContainingAndEstado(Pageable pageable, String denominacion, Estado estado);

}
