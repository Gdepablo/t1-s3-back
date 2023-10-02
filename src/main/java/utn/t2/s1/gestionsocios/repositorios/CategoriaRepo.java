package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import java.util.List;


public interface CategoriaRepo extends JpaRepository<Categoria,Long> {
    public Categoria findByNombreAndEstado(String nombre,Estado estado);
    public List<Categoria> findAllByEstado(Estado estado);

}
