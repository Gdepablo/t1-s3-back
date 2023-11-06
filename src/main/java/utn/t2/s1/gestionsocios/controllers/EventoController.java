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
import utn.t2.s1.gestionsocios.dtos.EventoDTO;
import utn.t2.s1.gestionsocios.dtos.RolDTO;
import utn.t2.s1.gestionsocios.modelos.Evento;
import utn.t2.s1.gestionsocios.modelos.Lugar;
import utn.t2.s1.gestionsocios.modelos.Rol;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.servicios.EventoServicio;
import utn.t2.s1.gestionsocios.servicios.LugarServicio;
import utn.t2.s1.gestionsocios.servicios.ParticipanteServicio;

import java.util.List;

@Tag(name = "Operaciones para los participantes", description = "Api para realizar las operaciones de alta, baja y modificacion de participantes")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema(allOf = Evento.class)) })
})
@RestController
@RequestMapping("/eventos")
@Validated
@CrossOrigin
public class EventoController {


    @Autowired
    ParticipanteServicio participanteServicio;

    @Autowired
    EventoServicio eventoServicio;

    @Autowired
    LugarServicio lugarServicio;


    @GetMapping()
    @Operation(summary = "Retorna los eventos de la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tipo de evento encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Rol.class)) }),
    })
    public ResponseEntity<Page<Evento>> verEventos(Pageable page){
        Page<Evento> eventos = eventoServicio.buscarTodos(page);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }


    @GetMapping(value = {"/search", "/search/"})
    @Operation(summary = "Retorna los Eventos filtrados y buscados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos Filtrados encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Evento.class)) }),
    })
    public ResponseEntity<Page<Evento>> verEventosFiltradoBuscado(Pageable pageable, @RequestParam(required = false) String nombre){

        Page<Evento> eventos = eventoServicio.buscarPorNombre(pageable, nombre);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    @Operation(summary = "Retorna el evento  correspondiente al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado" ,content = { @Content(mediaType = "application/json",schema = @Schema( implementation = Evento.class)) }),
            @ApiResponse(responseCode = "404", description = "El evento no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<Evento> verEvento(@PathVariable Long id) {

        if (eventoServicio.buscarPorId(id) == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Evento _evento = eventoServicio.buscarPorId(id);
        return new ResponseEntity<>(_evento, HttpStatus.OK);
    }


    @PostMapping()
    @Operation(summary = "Agrega un evento a la Base de datos")
    public ResponseEntity<Object> agregarEvento(@RequestBody @Valid EventoDTO eventoDTO){
//        if(eventoServicio.buscarPorNombre(eventoDTO.getNombre()) != null){
//            return new ResponseEntity<>("El evento '"+ eventoDTO.getNombre()+"' ya existe", HttpStatus.CREATED);
//        }

        Evento evento = eventoServicio.agregar(eventoDTO);



        return new ResponseEntity<>(evento, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Modifica un evento en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El evento no fue encontrada",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarEvento(@PathVariable Long id, @RequestBody EventoDTO eventoDTO){
        Evento eventoUpdate = eventoServicio.actualizar(eventoDTO, id);
        return new ResponseEntity<>(eventoUpdate , HttpStatus.OK);
    }

    @DeleteMapping(value = {"/{id}", "/{id}/"})
    @Operation(summary = "Elimina un evento en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "evento eliminado" ,content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> deleteEvento(@PathVariable Long id){
        eventoServicio.eliminar(id);
        return new ResponseEntity<>("Evento eliminado", HttpStatus.OK);
    }


}
