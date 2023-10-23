package utn.t2.s1.gestionsocios.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.converters.UsuarioConverter;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTOLogin;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTOSignUp;
import utn.t2.s1.gestionsocios.excepciones.UsuarioContraseniaException;
import utn.t2.s1.gestionsocios.excepciones.UsuarioNombreException;
import utn.t2.s1.gestionsocios.servicios.UsuarioServicio;
import utn.t2.s1.gestionsocios.modelos.Usuario;

import java.util.Optional;

@Tag(name = "Operaciones de sesión", description = "Api para realizar las operaciones de sesión")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@Validated
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    UsuarioServicio servicio;
    @Autowired
    UsuarioConverter usuarioConverter;

    @PostMapping("/login")
    @Operation(summary = "Ingresar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado" ,content = { @Content(mediaType = "application/json",schema = @Schema( implementation = Usuario.class)) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El usuario no fue encontrado",content = { @Content(schema = @Schema()) }),

    })
    public ResponseEntity<?> ingresar(@RequestBody UsuarioDTOLogin usuarioDTOLogin){

        Usuario usuario = new Usuario();

        try {
            usuario = servicio.buscarUsuario(usuarioDTOLogin);
        } catch (UsuarioNombreException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        } catch (UsuarioContraseniaException e) {
            return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Registrar usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado" ,content = { @Content(mediaType = "application/json",schema = @Schema( implementation = Usuario.class)) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),

    })
    public ResponseEntity<Object> registrar(@RequestBody UsuarioDTOSignUp usuarioDTOSignUp){
        Optional<Usuario> _usuario = servicio.buscarPorNombre(usuarioDTOSignUp.getNombre());

        if(_usuario.isEmpty()){
            return new ResponseEntity<>(servicio.agregar(usuarioDTOSignUp), HttpStatus.CREATED );
        }

        return  new ResponseEntity<>("el nombre de usuario ya existe", HttpStatus.BAD_REQUEST );
    }

}
