package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class DepartamentoDTO {

    @NotNull
    private String nombre;

    @NotNull
    private String objetivo;
}
