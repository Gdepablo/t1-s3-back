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
import utn.t2.s1.gestionsocios.dtos.AtributosDTO;
import utn.t2.s1.gestionsocios.excepciones.TipoException;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.servicios.CategoriaServicio;
import utn.t2.s1.gestionsocios.servicios.TipoSocioServicio;

@Tag(name = "Operaciones para las categorías y los tipos de los socios", description = "Api para realizar las operaciones de alta, baja y modificacion de categorías y tipos")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@RequestMapping("")
@Validated
@CrossOrigin
public class AtributosController {
    @Autowired
    CategoriaServicio categoriaServicio;
    @Autowired
    TipoSocioServicio tipoSocioServicio;

    @GetMapping("/categorias")
    @Operation(summary = "Retorna todos los nombres de las categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorías encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<Object> verNombresCategorias(){
        return new ResponseEntity<>(categoriaServicio.nombresCategoria(), HttpStatus.OK);
    }

    @PostMapping("/categorias")
    public ResponseEntity<Object> agregarCategoria(@RequestBody @Valid AtributosDTO categoriaDTO){ //TODO
        if(categoriaServicio.buscarPorNombre(categoriaDTO.getNombre()) != null){
            return new ResponseEntity<>("el nombre '"+ categoriaDTO.getNombre()+"' de categoria ya existe", HttpStatus.CREATED);
        }
        Categoria _categoria = new Categoria();
        _categoria.setNombre(categoriaDTO.getNombre());
        _categoria.setEstado(Estado.ACTIVO);
        categoriaServicio.agregar(_categoria);

        return new ResponseEntity<>(_categoria, HttpStatus.CREATED);
    }

    @DeleteMapping("/categorias/{id}")
    @Operation(summary = "Elimina una categoria en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria eliminada" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "La categoria no fue encontrada",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<Object> eliminarCategoria(@PathVariable Long id){ //TODO
        if (categoriaServicio.buscarPorId(id) == null){
            return new ResponseEntity<>("categoria no encontrada", HttpStatus.NOT_FOUND);
        }

        categoriaServicio.borrar(id);
        return new ResponseEntity<>("categoria eliminada", HttpStatus.OK);
    }

    @GetMapping("/tipos")
    @Operation(summary = "Retorna todos los nombres de los tipo de socios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<Object> verNombresTipos(){
        return new ResponseEntity<>(tipoSocioServicio.nombresSocios(), HttpStatus.OK);
    }

    @DeleteMapping("/tipos/{id}")
    @Operation(summary = "Elimina un tipo en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo eliminado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El tipo no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<Object> eliminarTipo(@PathVariable Long id){ //TODO
        if (tipoSocioServicio.buscarPorId(id) == null){
            return new ResponseEntity<>("Tipo no encontrado", HttpStatus.NOT_FOUND);
        }

        tipoSocioServicio.borrar(id);
        return new ResponseEntity<>("Tipo eliminado", HttpStatus.OK);
    }

    @PostMapping("/tipos")
    public ResponseEntity<Object> agregarTipoSocio(@RequestBody @Valid AtributosDTO categoriaDTO) throws TipoException {
        if(tipoSocioServicio.buscarPorNombre(categoriaDTO.getNombre()) != null){
            return new ResponseEntity<>("el nombre '"+ categoriaDTO.getNombre()+"' de tipo ya existe", HttpStatus.CREATED);
        }
        TipoSocio _tipo = new TipoSocio();
        _tipo.setNombre(categoriaDTO.getNombre());
        _tipo.setEstado(Estado.ACTIVO);
        tipoSocioServicio.agregar(_tipo);

        return new ResponseEntity<>(_tipo, HttpStatus.CREATED);
    }

}