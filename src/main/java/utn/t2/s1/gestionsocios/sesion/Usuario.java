package utn.t2.s1.gestionsocios.sesion;

import jakarta.persistence.*;
import lombok.*;
import utn.t2.s1.gestionsocios.modelos.Socio;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String contrasenia;
    @OneToOne
    private Socio socio;


}
