package utn.t2.s1.gestionsocios.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.sesion.Usuario;

@Getter
@Setter
public class UsuarioDTO {
    @NotNull
    @NotBlank( message = "Error, el usuario necesita un nombre")
    private String nombre;
    @NotNull
    @NotBlank
    private String contrasenia;
    @Schema( hidden = true)
    private Socio socio;

//    public Usuario toUsuario() {
//        Usuario usuario = new Usuario();
//        usuario.setNombre(this.nombre);
//        usuario.setContrasenia(this.contrasenia);
//        return usuario;
//    }
}
