package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import java.util.List;

public interface TipoSocioRepo extends JpaRepository<TipoSocio,Long> {
    public List<TipoSocio> findAllByEstado(Estado estado);
    public TipoSocio findByNombreAndEstado(String nombre, Estado estado);

}
