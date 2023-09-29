package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.sesion.Usuario;

import java.util.List;
import java.util.Optional;


public interface CategoriaRepo extends JpaRepository<Categoria,Long> {
    public Categoria findByNombreAndEstado(String nombre,Estado estado);
    public List<Categoria> findAllByEstado(Estado estado);
}
