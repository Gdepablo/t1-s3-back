package utn.t2.s1.gestionsocios.modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="socio")
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="denominacion")
    private String denominacion;
    @Column(name="telefono")
    private String telefono;
    @Enumerated(EnumType.STRING)
    @Column(name="tipo")
    private TipoSocio tipo;
    @Column(name="direccion")
    private String direccion;
    @Column(name="mail")
    private String mail;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="web")
    private String web;
    private LocalDate fechaAlta; //TODO formatear fecha dd-MM-yyyy
    @Column(name="logo")
    private String logo;
    //@ManyToOne(cascade = CascadeType.PERSIST)
    //@JoinColumn(name = "categoria_id",referencedColumnName = "id" ) //TODO ver de pasar solo la FK en todos los metodos
    //private Categoria categoria;
    //@NotNull
    @ElementCollection(targetClass = Categoria.class)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "categoria", joinColumns = @JoinColumn(name = "id_socio", referencedColumnName = "id"))
    @Column(name = "categoria", nullable = false)
    private Set<Categoria> categorias =new HashSet<>(); ;
}