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
import utn.t2.s1.gestionsocios.dtos.DepartamentoDTO;
import utn.t2.s1.gestionsocios.dtos.SubDepartamentoDTO;
import utn.t2.s1.gestionsocios.excepciones.DepartamentoException;
import utn.t2.s1.gestionsocios.excepciones.SubDepartamentoException;
import utn.t2.s1.gestionsocios.modelos.Departamento;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.SubDepartamento;
import utn.t2.s1.gestionsocios.modelos.Usuario;
import utn.t2.s1.gestionsocios.servicios.DepartamentoServicio;

import java.util.List;

@Tag(name = "Operaciones de sesión", description = "Api para realizar las operaciones de sesión")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@Validated
@RequestMapping("/departamento")
@CrossOrigin
public class DepartamentoController {

    @Autowired
    DepartamentoServicio departamentoServicio;


    @GetMapping()
    @Operation(summary = "Retorna todos los Departamento de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamentos encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Socio.class)) }),
    })
    public ResponseEntity<Page<Departamento>> verDepartamentos(Pageable pageable){
        Page<Departamento> departamentos = departamentoServicio.traerDepartamentos(pageable);
        return new ResponseEntity<>(departamentos , HttpStatus.OK);
    }


    @GetMapping("/{idDepartamento}")
    @Operation(summary = "Retorna todos los SubDepartamentos de un departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autoridades encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<Departamento> verAutoridadesPorDepartamento(@PathVariable Long idDepartamento) throws DepartamentoException {
        Departamento departamento = departamentoServicio.buscarPorId(idDepartamento);
        return new ResponseEntity<>(departamento , HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Ingresar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamento encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "El Departamento no fue encontrado", content = {@Content(schema = @Schema())}),
    })
    public ResponseEntity<?> agregarSubDepartamento(@RequestBody DepartamentoDTO departamentoDTO){
        Departamento departamento = departamentoServicio.agregar(departamentoDTO);
        return new ResponseEntity<>(departamento, HttpStatus.OK);
    }

    @DeleteMapping("/{idDepartamento}")
    @Operation(summary = "Eliminar SubDepartamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departamento eliminado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
    })
    public ResponseEntity<Object> eliminarAutoridad(@PathVariable Long idDepartamento)throws DepartamentoException {
        if (departamentoServicio.buscarPorId(idDepartamento) == null) {
            return new ResponseEntity<>("Departamento no encontrado", HttpStatus.NOT_FOUND);
        }

        departamentoServicio.eliminarDepartamento(idDepartamento);
        return new ResponseEntity<>("Departamento eliminado", HttpStatus.OK);
    }


    @PutMapping("/{idDepartamento}")
    @Operation(summary = "Modifica una Departamento en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departamento modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El Usuario no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long idDepartamento, @RequestBody DepartamentoDTO departamentoDTO){
        try {
            Departamento departamento = departamentoServicio.actualizar(idDepartamento, departamentoDTO);
            return new ResponseEntity<>(departamento, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }



}

