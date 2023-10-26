package utn.t2.s1.gestionsocios.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.converters.RolConverter;
import utn.t2.s1.gestionsocios.converters.TipoDeUsuarioConverter;
import utn.t2.s1.gestionsocios.dtos.RolDTO;
import utn.t2.s1.gestionsocios.dtos.TipoDeUsuarioDTO;
import utn.t2.s1.gestionsocios.modelos.Rol;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;
import utn.t2.s1.gestionsocios.servicios.RolServicio;
import utn.t2.s1.gestionsocios.servicios.TipoDeUsuarioServicio;

import java.util.List;


@Tag(name = "Operaciones para los socios", description = "Api para realizar las operaciones de alta, baja y modificacion de un socio")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@RequestMapping("/roles")
@Validated
@CrossOrigin
public class RolController {


    @Autowired
    RolServicio rolServicio;

    @Autowired
    RolConverter rolConverter;



    @GetMapping()
    @Operation(summary = "Retorna todos los tipo de usuario de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tipo de usuario encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Socio.class)) }),
    })
    public ResponseEntity<List<Rol>> verRoles(){

        List<Rol> roles = rolServicio.buscarTodos();
        return new ResponseEntity<>(roles , HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<Object> agregarTipoDeUsuario(@RequestBody @Valid RolDTO rolDTO){
        if(rolServicio.buscarPorNombre(rolDTO.getNombre()) != null){
            return new ResponseEntity<>("el rol '"+ rolDTO.getNombre()+"' ya existe", HttpStatus.CREATED);
        }

        Rol rol = rolServicio.agregar(rolDTO);

        return new ResponseEntity<>(rol, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Modifica un tipo de usuario en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de usuario modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El tipo de usuario no fue encontrada",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarTipoDeUsuario(@PathVariable Long id, @RequestBody RolDTO rolDTO){
        try {
            Rol rolUpdate = rolServicio.actualizar(rolDTO, id);
            return new ResponseEntity<>(rolUpdate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = {"/{id}", "/{id}/"})
    @Operation(summary = "Elimina un Rol en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol eliminado" ,content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> deleteRol(@PathVariable Long id){
        try {
            rolServicio.eliminar(id);
            return new ResponseEntity<>("Rol eliminado", HttpStatus.OK);
//            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }


}