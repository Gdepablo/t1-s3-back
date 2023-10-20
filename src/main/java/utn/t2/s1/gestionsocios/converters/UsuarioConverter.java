package utn.t2.s1.gestionsocios.converters;

import org.springframework.stereotype.Component;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTO;
import utn.t2.s1.gestionsocios.sesion.Usuario;

@Component
public class UsuarioConverter {

    public Usuario toUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setContrasenia(usuarioDTO.getContrasenia());
        return usuario;
    }

}
