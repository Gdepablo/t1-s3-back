package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link utn.t2.s1.gestionsocios.modelos.Participante}
 */
@Value
public class ParticipanteDTO implements Serializable {
    @NotNull
    @NotEmpty
    String nombre;
    @NotNull
    @NotEmpty
    String apellido;
    @NotNull
    @Email
    @NotEmpty
    String mail;
    @NotNull
    @NotEmpty
    String referente;
    @NotNull
    EmpresaEventoDTO empresaEvento;
    @NotNull
    EventoDTO evento;
}