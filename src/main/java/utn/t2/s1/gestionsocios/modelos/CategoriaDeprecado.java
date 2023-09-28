package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Entity
//@Table(name="categoria")
public class CategoriaDeprecado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank( message = "Error, la denominacion de la categoria es obligatoria")
    private String descripcion;
    @Min(value = 1,message = "Error, la prioridad minima debe ser 1")
    private int prioridad;
}
