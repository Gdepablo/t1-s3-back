package utn.t2.s1.gestionsocios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTO;
import utn.t2.s1.gestionsocios.excepciones.UsuarioContraseniaException;
import utn.t2.s1.gestionsocios.excepciones.UsuarioNombreException;
import utn.t2.s1.gestionsocios.repositorios.UsuarioRepo;
import utn.t2.s1.gestionsocios.sesion.Usuario;

import java.util.Optional;

@Service
public class UsuarioServicio {
    @Autowired
    UsuarioRepo repo;

    public Usuario agregar(Usuario usuario){
        return repo.save(usuario);
    }

    public Optional<Usuario> buscarUsuario(UsuarioDTO usuarioDTO) throws UsuarioNombreException, UsuarioContraseniaException {
        Optional<Usuario> usuario = buscarPorNombre(usuarioDTO.getNombre());
        if (usuario.isPresent()){

            if(!usuario.get().getContrasenia().equals(usuarioDTO.getContrasenia())){ //no coinciden la contraseña con el nombre
                throw new UsuarioContraseniaException();
            }

        }else{ //no existe el usuario
            throw new UsuarioNombreException(  );
        }

        return usuario;
    }

    public Optional<Usuario> buscarPorNombre(String nombreUsuario){
        return repo.findByNombre(nombreUsuario);
    }
}
