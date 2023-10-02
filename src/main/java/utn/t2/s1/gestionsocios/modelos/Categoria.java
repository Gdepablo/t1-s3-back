package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="categoria")
public class Categoria extends Persistente {

    @NotBlank( message = "Error, el nombre de la categoria es obligatorio")
    private String nombre;

}
