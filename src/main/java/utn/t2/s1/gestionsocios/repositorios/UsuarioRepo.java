package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import utn.t2.s1.gestionsocios.sesion.Usuario;

import java.util.Optional;

public interface UsuarioRepo extends CrudRepository<Usuario, Long> {
    @Query( value = "SELECT * FROM usuario WHERE nombre = :nombreUsuario", nativeQuery = true)
    Optional<Usuario> buscarPorNombre(@Param("nombreUsuario") String nombreUsuario);

}
