package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.Socio;
@Repository
public interface CategoriaRepo extends CrudRepository<Categoria,Long>  {
}
