package utn.t2.s1.gestionsocios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.converters.UsuarioConverter;
import utn.t2.s1.gestionsocios.dtos.TipoDeUsuarioDTO;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTOLogin;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTOSignUp;
import utn.t2.s1.gestionsocios.excepciones.UsuarioContraseniaException;
import utn.t2.s1.gestionsocios.excepciones.UsuarioNombreException;
import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;
import utn.t2.s1.gestionsocios.repositorios.TipoDeUsuarioRepo;
import utn.t2.s1.gestionsocios.repositorios.UsuarioRepo;
import utn.t2.s1.gestionsocios.modelos.Usuario;

import java.util.Optional;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepo repo;
    @Autowired
    private TipoDeUsuarioRepo tipoDeUsuarioRepo;
    @Autowired
    private UsuarioConverter usuarioConverter;

    public Usuario agregar(UsuarioDTOSignUp usuarioDTOSignUp){

        Optional<TipoDeUsuario> optionalTipoDeUsuario = tipoDeUsuarioRepo.findById(usuarioDTOSignUp.getTipoDeUsuarioId());
        if (!optionalTipoDeUsuario.isPresent()) {
            throw new RuntimeException("Tipo de Usuario no encontrado");
        }

        Usuario usuario = usuarioConverter.toUsuario(usuarioDTOSignUp);
        usuario.setTipoDeUsuario(optionalTipoDeUsuario.get());

        return repo.save(usuario);
    }

    public Usuario buscarUsuario(UsuarioDTOLogin usuarioDTOLogin) throws UsuarioNombreException, UsuarioContraseniaException {
        Optional<Usuario> usuario = buscarPorNombre(usuarioDTOLogin.getNombre());
        if (usuario.isPresent()){

            if(!usuario.get().getContrasenia().equals(usuarioDTOLogin.getContrasenia())){ //no coinciden la contraseña con el nombre
                throw new UsuarioContraseniaException();
            }

        }else{ //no existe el usuario
            throw new UsuarioNombreException(  );
        }
        Usuario usuarioUpdate = usuario.get();
        return usuarioUpdate;
    }

    public Optional<Usuario> buscarPorNombre(String nombreUsuario){
        return repo.findByNombre(nombreUsuario);
    }
}
