package utn.t2.s1.gestionsocios.dtos;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link utn.t2.s1.gestionsocios.modelos.EmpresaEvento}
 */
@Value
public class EmpresaEventoDTO implements Serializable {
    SocioDTO socio;
    String otraEmpresa;
}