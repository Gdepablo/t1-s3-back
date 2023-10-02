package utn.t2.s1.gestionsocios.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.dtos.SocioDTO;
import utn.t2.s1.gestionsocios.excepciones.CategoriaException;
import utn.t2.s1.gestionsocios.excepciones.TipoException;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.servicios.CategoriaServicio;
import utn.t2.s1.gestionsocios.servicios.SocioServicio;
import utn.t2.s1.gestionsocios.servicios.TipoSocioServicio;
import java.util.Set;

@Tag(name = "Operaciones para los socios", description = "Api para realizar las operaciones de alta, baja y modificacion de un socio")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@RequestMapping("/socios")
@Validated
@CrossOrigin
public class SocioController {

    @Autowired
    SocioServicio servicio;
    @Autowired
    CategoriaServicio categoriaServicio;
    @Autowired
    TipoSocioServicio tipoServicio;
    @GetMapping()
    @Operation(summary = "Retorna todos los socios de la base de datos") //TODO paginacion
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socios encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Socio.class)) }),
    })
    public ResponseEntity<Object> verSocios(Pageable pageable){

        Page<Socio> socios = servicio.buscarTodos(pageable);
        return new ResponseEntity<>(socios.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna el socio correspondiente al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio encontrado" ,content = { @Content(mediaType = "application/json",schema = @Schema( implementation = Socio.class)) }),
            @ApiResponse(responseCode = "404", description = "El socio no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<Socio> verSocio(@PathVariable Long id) {

        if (servicio.buscarPorId(id) == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Socio _socio = servicio.buscarPorId(id);
        return new ResponseEntity<>(_socio, HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Inserta un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio creado" ,content = { @Content(mediaType = "application/json",schema = @Schema( implementation = Socio.class)) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "La categoria no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El tipo no fue encontrada",content = { @Content(schema = @Schema()) }),

    })
    public ResponseEntity<Object> agregarSocio(@RequestBody @Valid SocioDTO socioDTO){ //TODO DTO socio

        Set<Categoria> categorias = null;
        TipoSocio tipo = null;
        try {
            categorias = categoriaServicio.stringSetToCategoriaSet(socioDTO.getCategorias());
        } catch (CategoriaException e) {
            return new ResponseEntity<>("Una de las categorias no existe en la base de datos", HttpStatus.NOT_FOUND);
        }
        tipo = tipoServicio.buscarPorNombre(socioDTO.getTipo());
        if(tipo == null){
            return new ResponseEntity<>("El tipo no existe en la base de datos", HttpStatus.NOT_FOUND);
        }

        Socio  _socio = socioDTO.toSocio(categorias, tipo);
        _socio.setEstado(Estado.ACTIVO);

        servicio.agregar(_socio);

        return new ResponseEntity<>(_socio, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Modifica un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El socio no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "La categoria no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El tipo no fue encontrada",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<Object> modificarSocio( @PathVariable Long id, @RequestBody @Valid SocioDTO socioDTO){

        if (servicio.buscarPorId(id) == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); //TODO ver si se devuelve nulo o una cadena de texto
        }
        Set<Categoria> categorias = null;
        TipoSocio tipo = null;
        try {
            categorias = categoriaServicio.stringSetToCategoriaSet(socioDTO.getCategorias());
        } catch (CategoriaException e) {
            return new ResponseEntity<>("Una de las categorias no existe en la base de datos", HttpStatus.NOT_FOUND);
        }
        tipo = tipoServicio.buscarPorNombre(socioDTO.getTipo());
        if(tipo == null){
            return new ResponseEntity<>("El tipo no existe en la base de datos", HttpStatus.NOT_FOUND);
        }
//        try {
//            tipo = tipoServicio.buscarPorNombre(socioDTO.getTipo());
//        } catch (TipoException e) {
//            return new ResponseEntity<>("El tipo no existe en la base de datos", HttpStatus.NOT_FOUND);
//        }
        Socio _socio = socioDTO.toSocio(categorias, tipo);
        _socio.setEstado(Estado.ACTIVO);

        servicio.modificar(id,_socio);

        return new ResponseEntity<>(_socio, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio eliminado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El socio no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<String> eliminarSocio(@PathVariable Long id){

        if (servicio.buscarPorId(id) == null){
            return new ResponseEntity<>("Socio no encontrado", HttpStatus.NOT_FOUND);
        }

        servicio.borrar(id);
        return new ResponseEntity<>("Socio eliminado", HttpStatus.OK);
    }


}
