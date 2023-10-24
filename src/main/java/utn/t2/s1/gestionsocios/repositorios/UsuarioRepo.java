package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;
import utn.t2.s1.gestionsocios.modelos.Usuario;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.Optional;

public interface UsuarioRepo extends CrudRepository<Usuario, Long> {

    Page<Usuario> findAllByEstado(Pageable pageable, Estado estado);

    Optional<Usuario> findByNombre(String nombreUsuario);

    Usuario findByIdAndEstado(Long id, Estado estado);

}
