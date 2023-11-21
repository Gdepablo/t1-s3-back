package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Reserva;
import utn.t2.s1.gestionsocios.modelos.Rol;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepo extends JpaRepository<Reserva,Long> {

    Optional<Reserva> findByIdAndEstado(Long id, Estado estado);

    Optional<Reserva> findByCodigoDeSeguimientoAndEstado(String codigoDeSeguimiento, Estado estado);

    List<Reserva> findAllByEstado(Estado estado);

//    List<Reserva> findAllByFechaIsBetweenAndEstado(LocalDateTime fechaInicio, LocalDateTime fechaFin, Estado estado);

    List<Reserva> findAllByFechaInicioIsBetweenAndEstado(LocalDateTime fechaInicio, LocalDateTime fechaFin, Estado estado);



}
