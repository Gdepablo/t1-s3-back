package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import utn.t2.s1.gestionsocios.modelos.Socio;

public interface SocioRepo extends JpaRepository<Socio, Long> {

}
