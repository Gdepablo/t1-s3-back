package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link utn.t2.s1.gestionsocios.modelos.Reserva}
 */
@Data
public class ReservaDto implements Serializable {
    @NotNull
    @NotEmpty
    @Length(max = 500)
    String descripcion;
    @NotNull
    @FutureOrPresent
    LocalDateTime fecha;
    @NotNull
    Float duracion;
    Set<RecursoDto> recursos;
    EspacioFisicoDto espacioFisico;
    EstadoReservaDto estadoReserva;
    EncargadoDto encargado;
}