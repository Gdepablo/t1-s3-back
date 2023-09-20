package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.repository.CrudRepository;
import utn.t2.s1.gestionsocios.modelos.Socio;

public interface SocioRepo extends CrudRepository <Socio, Long> {
}
