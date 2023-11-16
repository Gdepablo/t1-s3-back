package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.converters.ReservaConverter;
import utn.t2.s1.gestionsocios.converters.RolConverter;
import utn.t2.s1.gestionsocios.dtos.ReservaDto;
import utn.t2.s1.gestionsocios.dtos.RolDTO;
import utn.t2.s1.gestionsocios.modelos.Reserva;
import utn.t2.s1.gestionsocios.modelos.Rol;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.*;

import java.util.List;
import java.util.Optional;


@Service
public class ReservaServicio {

    @Autowired
    ReservaRepo reservaRepo;

    @Autowired
    RecursosRepo recursosRepo;

    @Autowired
    EstadoReservaRepo estadoReservaRepo;

    @Autowired
    EspacioFisicoRepo espacioFisicoRepo;

    @Autowired
    EncargadoRepo encargadoRepo;


    @Autowired
    ReservaConverter reservaConverter;


    public List<Reserva> buscarTodos() {
        return reservaRepo.findAllByEstado(Estado.ACTIVO) ;
    }


    public Reserva agregar(ReservaDto reservaDto) {
        Reserva reserva = reservaConverter.toReserva(reservaDto);

        reserva.setEstado(Estado.ACTIVO);

        try {
            reserva = reservaRepo.save(reserva);
        } catch (DataIntegrityViolationException e) {
            // Manejar la excepción de violación de restricción única (rol duplicado)
            throw new IllegalArgumentException("El nombre del rol ya existe en la base de datos.");
        }
        return reserva;
    }




    public Reserva actualizar(ReservaDto reservaDto, long id) {
        Optional<Reserva> optionalReserva = reservaRepo.findById(id);
        if (!optionalReserva.isPresent()) {
            throw new EntityNotFoundException("Reserva no encontrada");
        }

        Reserva reservaUpdate = optionalReserva.get();


//        reservaUpdate.setNombreRol(reservaDto.getNombre());

        reservaUpdate = reservaRepo.save(reservaUpdate);

        return reservaUpdate;
    }


    public void eliminar(long id) {

        Optional<Reserva> optionalReserva = reservaRepo.findById(id);
        if (!optionalReserva.isPresent() || optionalReserva.get().getEstado() == Estado.ELIMINADO) {
            throw new EntityNotFoundException("Reserva no encontrado");
        }

        Reserva reserva = optionalReserva.get();
        reserva.setEstado(Estado.ELIMINADO);
        reservaRepo.save(reserva);
    }

}
