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
import utn.t2.s1.gestionsocios.modelos.AutoridadDepartamento;
import utn.t2.s1.gestionsocios.modelos.AutoridadSubDepartamento;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.Usuario;
import utn.t2.s1.gestionsocios.servicios.AutoridadSubDepartamentoServicio;

@Tag(name = "Operaciones de sesión", description = "Api para realizar las operaciones de sesión")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@Validated
@RequestMapping("/subdepartamento/autoridades")
@CrossOrigin
public class AutoridadSubDepartamentoController {

    @Autowired
    AutoridadSubDepartamentoServicio autoridadSubDepartamentoServicio;


    @GetMapping("/{idSubDepartamento}")
    @Operation(summary = "Retorna todos las autoridades de un subdepartamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autoridades encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<Page<AutoridadSubDepartamento>> verAutoridadesPorDepartamento(Pageable pageable, @PathVariable Long idSubDepartamento){
        Page<AutoridadSubDepartamento> autoridades = autoridadSubDepartamentoServicio.traerAutoridadesPorSubDepartamento(pageable, idSubDepartamento);
        return new ResponseEntity<>(autoridades , HttpStatus.OK);
    }

    @PostMapping("/{idSubDepartamento}")
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

    @DeleteMapping("/{idAutoridad}")
    @Operation(summary = "Eliminar Autoridad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autoridad eliminado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
    })
    public ResponseEntity<Object> eliminarAutoridad(@PathVariable Long idAutoridad) {
        if (autoridadSubDepartamentoServicio.buscarPorId(idAutoridad) == null) {
            return new ResponseEntity<>("Autoridad no encontrado", HttpStatus.NOT_FOUND);
        }

        autoridadSubDepartamentoServicio.eliminarAutoridadSubDepartamento(idAutoridad);
        return new ResponseEntity<>("Autoridad eliminado", HttpStatus.OK);
    }


    @PutMapping("/{idAutoridad}")
    @Operation(summary = "Modifica una Autoridad en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autoridad modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El Usuario no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long idAutoridad, @RequestBody AutoridadDTO autoridadDTO){
        try {
            AutoridadSubDepartamento autoridadSubDepartamento = autoridadSubDepartamentoServicio.actualizar(idAutoridad, autoridadDTO);
            return new ResponseEntity<>(autoridadSubDepartamento, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }



}