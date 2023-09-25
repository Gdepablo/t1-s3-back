package utn.t2.s1.gestionsocios.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.dtos.SocioDTO;
import utn.t2.s1.gestionsocios.modelos.CategoriaDeprecado;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.servicios.CategoriaServicio;
import utn.t2.s1.gestionsocios.servicios.SocioServicio;

import java.util.List;

@RestController
@RequestMapping("/socios")
@Validated
public class SocioController {

    @Autowired
    SocioServicio servicio;

    //Autowired
    //CategoriaServicio categoriaServicio;

    @GetMapping()
    public List<Socio> verSocios(){
        return servicio.buscarTodos();
    }

    @GetMapping("/{id}")
    public Socio verSocio(@PathVariable Long id) {
        return servicio.buscarPorId(id);
    }

    @Operation(summary = "Inserta un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio creado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            //@ApiResponse(responseCode = "404", description = "La categoria del socio no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
            })
    @PostMapping()
    public ResponseEntity<String> agregarSocio(@RequestBody @Valid SocioDTO socioDTO){ //TODO DTO socio

        /*
        CategoriaDeprecado categoria = categoriaServicio.buscarPorId(socioDTO.getCategoria());

        if(categoria==null ){
            return new ResponseEntity<>("La categoria del socio no fue encontrada", HttpStatus.NOT_FOUND);
        }*/

        Socio _socio = socioDTO.toSocio();
        //_socio.setCategoria(categoria);
        servicio.agregar(_socio);

        return new ResponseEntity<>("Socio creado", HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            //@ApiResponse(responseCode = "404", description = "La categoria del socio no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> modificarSocio( @PathVariable Long id, @RequestBody @Valid SocioDTO socioDTO){
        /*
        CategoriaDeprecado categoria = categoriaServicio.buscarPorId(socioDTO.getCategoriaId());

        if(categoria==null ){
            return new ResponseEntity<>("La categoria del socio no fue encontrada", HttpStatus.NOT_FOUND);
        }*/

        Socio _socio = socioDTO.toSocio();
        //_socio.setCategoria(categoria);
        servicio.modificar(id,_socio);
        return new ResponseEntity<>("Socio modificado", HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Elimina un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio eliminado" ,content = { @Content(schema = @Schema()) }),
           // @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
           // @ApiResponse(responseCode = "404", description = "La categoria del socio no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("/{id}")
    public String eliminarSocio(@PathVariable Long id){
        servicio.borrar(id);
        return "ok";
    }
/*
    @PostMapping("/categorias")
    public String agregarCategoria(@RequestBody CategoriaDeprecado categoria){
        categoriaServicio.agregar(categoria);
        return "ok";
    }
    @Operation(summary = "Muestra la categoria correspondiente al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "La categoria fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDeprecado> buscarCategoria(@PathVariable Long id){
        CategoriaDeprecado categoria = categoriaServicio.buscarPorId(id);
        if (categoria == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoria, HttpStatus.ACCEPTED);
    }
    @GetMapping("/categorias")
    public List<CategoriaDeprecado> verCategorias(){
        return categoriaServicio.buscarTodas();
    }
    @DeleteMapping("/categorias/{id}")
    public String eliminarCategoria(@PathVariable Long id){
        categoriaServicio.borrar(id);
        return "ok";
    }

    @PutMapping("/categorias/{id}")
    public String modificarCategoria(@PathVariable Long id, @RequestBody CategoriaDeprecado categoria){
        categoriaServicio.modificar(id,categoria);
        return "ok";
    }
*/
}
