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
import utn.t2.s1.gestionsocios.modelos.Socio;
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

//    @PostMapping("")
//    public ResponseEntity<Object> agregarCategoria(){ //TODO
//        return new ResponseEntity<>(null,HttpStatus.OK);
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Object>modificarCategoria(@PathVariable Long id){ //TODO
//        return new ResponseEntity<>(null,HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> eliminarCategoria(@PathVariable Long id){ //TODO
//        return new ResponseEntity<>(null,HttpStatus.OK);
//    }

    @GetMapping("/tipos")
    @Operation(summary = "Retorna todos los nombres de los tipo de socios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<Object> verNombresTipos(){
        return new ResponseEntity<>(tipoSocioServicio.nombresSocios(), HttpStatus.OK);
    }
}
