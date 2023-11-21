package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.converters.EncargadoConverter;
import utn.t2.s1.gestionsocios.converters.ReservaConverter;
import utn.t2.s1.gestionsocios.converters.RolConverter;
import utn.t2.s1.gestionsocios.dtos.*;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


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

    @Autowired
    EncargadoConverter encargadoConverter;


    public List<Reserva> buscarTodos() {
        return reservaRepo.findAllByEstado(Estado.ACTIVO) ;
    }


    public Reserva agregar(ReservaDto reservaDto) {
        Reserva reserva = reservaConverter.toReserva(reservaDto);

        reserva.setEstado(Estado.ACTIVO);
        reserva.setEstadoReserva(estadoReservaRepo.findByNombreContainsAndEstado("PENDIENTE", Estado.ACTIVO).orElseThrow(() -> new RuntimeException("Estado reserva no encontrado")));
        Encargado nuevoEncargado = encargadoConverter.toEncargado(reservaDto.getEncargado());
        nuevoEncargado.setEstado(Estado.ACTIVO);
        reserva.setEncargado(nuevoEncargado);



        reserva.setEspacioFisico(espacioFisicoRepo.findByIdAndEstado(reservaDto.getEspacioFisico().getId(), Estado.ACTIVO).orElseThrow(() -> new RuntimeException("Espacio Fisico no encontrado")));

//
//        Set<Recurso> recursos = new HashSet<>();
//
//        //recorre la lista de recursos que llega por dto y busca por id llenando la lista recursos
//        reservaDto.getRecursos().forEach( r-> recursos.add((recursosRepo.findByIdAndEstado(  r.getId(), Estado.ACTIVO)).orElseThrow(() -> new RuntimeException("Estado no encontrado"))));
//
//


        List<Recurso> listaRecursos = recursosRepo.findAllById(reservaDto.getRecursos().stream().map(RecursoDto::getId).collect(Collectors.toList()));

        reserva.setRecursos(listaRecursos);


        reserva = reservaRepo.save(reserva);
        return reserva;
    }


    public Reserva buscarPorId(long id) {
        Optional<Reserva> optionalReserva = reservaRepo.findByIdAndEstado(id, Estado.ACTIVO);
        if (!optionalReserva.isPresent() || optionalReserva.get().getEstado() == Estado.ELIMINADO) {
            throw new EntityNotFoundException("Reserva no encontrado");
        }

        return optionalReserva.get();
    }


    public Reserva buscarPorCodigoDeSeguimiento(String codigoDeSeguimiento) {
        Optional<Reserva> optionalReserva = reservaRepo.findByCodigoDeSeguimientoAndEstado(codigoDeSeguimiento, Estado.ACTIVO);
        if (!optionalReserva.isPresent() || optionalReserva.get().getEstado() == Estado.ELIMINADO) {
            throw new EntityNotFoundException("Reserva no encontrado");
        }

        return optionalReserva.get();
    }


    public Reserva actualizar(ReservaDto reservaDto, long id) {
        Optional<Reserva> optionalReserva = reservaRepo.findById(id);
        if (!optionalReserva.isPresent()) {
            throw new EntityNotFoundException("Reserva no encontrada");
        }

        Reserva reservaUpdate = optionalReserva.get();

        reservaUpdate = reservaConverter.toReserva(reservaDto, reservaUpdate);
        reservaUpdate.setEncargado(encargadoConverter.toEncargado(reservaDto.getEncargado(), reservaUpdate.getEncargado()));
        reservaUpdate.setEspacioFisico(espacioFisicoRepo.findByIdAndEstado(reservaDto.getEspacioFisico().getId(), Estado.ACTIVO).orElseThrow(() -> new RuntimeException("Espacio Fisico no encontrado")));

        List<Recurso> listaRecursos = recursosRepo.findAllById(reservaDto.getRecursos().stream().map(RecursoDto::getId).collect(Collectors.toList()));

        reservaUpdate.setRecursos(listaRecursos);


        reservaUpdate = reservaRepo.save(reservaUpdate);

        return reservaUpdate;
    }



    public Reserva cambiarEstadoEvento(long id, EstadoReservaDto estadoReservaDto) {
        Optional<Reserva> optionalReserva = reservaRepo.findById(id);
        if (!optionalReserva.isPresent()) {
            throw new EntityNotFoundException("Evento no encontrado");
        }

        Reserva reservaUpdate = optionalReserva.get();

        Optional<EstadoReserva> optionalEstadoReserva = estadoReservaRepo.findByIdAndEstado(estadoReservaDto.getId(), Estado.ACTIVO);
        if (!optionalEstadoReserva.isPresent()) {
            throw new EntityNotFoundException("Estado Reserva no encontrado");
        }

        EstadoReserva estadoReserva = optionalEstadoReserva.get();

        reservaUpdate.setEstadoReserva(estadoReserva);
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

