package utn.t2.s1.gestionsocios.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.dtos.AutoridadDTO;
import utn.t2.s1.gestionsocios.dtos.SubDepartamentoDTO;
import utn.t2.s1.gestionsocios.excepciones.SubDepartamentoException;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.servicios.AutoridadDepartamentoServicio;
import utn.t2.s1.gestionsocios.servicios.AutoridadSubDepartamentoServicio;
import utn.t2.s1.gestionsocios.servicios.SubDepartamentoServicio;

@Tag(name = "Operaciones de sesión", description = "Api para realizar las operaciones de sesión")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@Validated
@RequestMapping("/subdepartamento")
@CrossOrigin
public class SubDepartamentoController {

    @Autowired
    SubDepartamentoServicio subDepartamentoServicio;

    @Autowired
    AutoridadSubDepartamentoServicio autoridadSubDepartamentoServicio;



    @GetMapping("/{idSubDepartamento}")
    @Operation(summary = "Retorna todos los SubDepartamentos de un departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autoridades encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<SubDepartamento> verAutoridadesPorDepartamento(@PathVariable Long idSubDepartamento) throws SubDepartamentoException {
        SubDepartamento subDepartamento = subDepartamentoServicio.buscarPorId(idSubDepartamento);
        return new ResponseEntity<>(subDepartamento , HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Ingresar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SubDepartamento encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "El SubDepartamento no fue encontrado", content = {@Content(schema = @Schema())}),
    })
    public ResponseEntity<?> agregarSubDepartamento(@RequestBody SubDepartamentoDTO subDepartamentoDTO){
        SubDepartamento subDepartamento = subDepartamentoServicio.agregar(subDepartamentoDTO);
        return new ResponseEntity<>(subDepartamento, HttpStatus.OK);
    }

    @DeleteMapping("/{idSubDepartamento}")
    @Operation(summary = "Eliminar SubDepartamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SubDepartamento eliminado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
    })
    public ResponseEntity<Object> eliminarAutoridad(@PathVariable Long idSubDepartamento)throws SubDepartamentoException {
        if (subDepartamentoServicio.buscarPorId(idSubDepartamento) == null) {
            return new ResponseEntity<>("SubDepartamento no encontrado", HttpStatus.NOT_FOUND);
        }

        subDepartamentoServicio.eliminarSubDepartamento(idSubDepartamento);
        return new ResponseEntity<>("SubDepartamento eliminado", HttpStatus.OK);
    }


    @PutMapping("/{idSubDepartamento}")
    @Operation(summary = "Modifica una SubDepartamento en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SubDepartamento modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El Usuario no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long idSubDepartamento, @RequestBody SubDepartamentoDTO subDepartamentoDTO){
        try {
            SubDepartamento subDepartamento = subDepartamentoServicio.actualizar(idSubDepartamento, subDepartamentoDTO);
            return new ResponseEntity<>(subDepartamento, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }

    //---------------------------------------------------------------------------------------------------------------

    @GetMapping("/{idSubDepartamento}/autoridades")
    @Operation(summary = "Retorna todos las autoridades de un subdepartamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autoridades encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<Page<AutoridadSubDepartamento>> verAutoridadesPorDepartamento(Pageable pageable, @PathVariable Long idSubDepartamento){
        Page<AutoridadSubDepartamento> autoridades = autoridadSubDepartamentoServicio.traerAutoridadesPorSubDepartamento(pageable, idSubDepartamento);
        return new ResponseEntity<>(autoridades , HttpStatus.OK);
    }

    @PostMapping("/{idSubDepartamento}/autoridades")
    @Operation(summary = "Ingresar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autoridad encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "La autoridad no fue encontrado", content = {@Content(schema = @Schema())}),
    })
    public ResponseEntity<?> agregarAutoridad(@PathVariable Long idSubDepartamento, @RequestBody AutoridadDTO autoridadDTO){
        AutoridadSubDepartamento autoridadSubDepartamento = autoridadSubDepartamentoServicio.agregar(idSubDepartamento, autoridadDTO);
        return new ResponseEntity<>(autoridadSubDepartamento, HttpStatus.OK);
    }



}

