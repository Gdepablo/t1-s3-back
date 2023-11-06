package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import utn.t2.s1.gestionsocios.persistencia.EstadoEvento;
import utn.t2.s1.gestionsocios.persistencia.Modalidad;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link utn.t2.s1.gestionsocios.modelos.Evento}
 */
@Value
public class EventoDTO implements Serializable {
    @NotNull
    @FutureOrPresent
    Date fechaInicio;

    @FutureOrPresent
    Date fechaFin;

//    String lugar;

    LugarDTO lugar;

    @NotNull
    @NotEmpty
    String descripcion;

    @NotNull
    Modalidad modalidad;

    EstadoEvento estadoEvento;

    @NotNull
    @NotEmpty
    String linkInscripcion;

    @NotNull
    @NotEmpty
    String nombre;
}