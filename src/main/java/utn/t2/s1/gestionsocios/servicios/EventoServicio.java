package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.dtos.EventoDTO;
import utn.t2.s1.gestionsocios.dtos.ParticipanteDTO;
import utn.t2.s1.gestionsocios.modelos.Lugar;
import utn.t2.s1.gestionsocios.modelos.Participante;
import utn.t2.s1.gestionsocios.modelos.Evento;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.EventoRepo;
import java.util.Optional;


@Service
public class EventoServicio {

    @Autowired
    private EventoRepo eventoRepo;

    @Autowired
    private LugarServicio lugarServicio;



    public Page<Evento> buscarTodos(Pageable page) {
        return eventoRepo.findAllByEstado(page , Estado.ACTIVO) ;
    }


    public Evento buscarPorId(Long id) {
        return eventoRepo.findByIdAndEstado(id, Estado.ACTIVO).orElseThrow( () -> new EntityNotFoundException("Evento no encontrado"));
    }



    public Page<Evento> buscarPorNombre(Pageable page, String nombre) {
        return eventoRepo.findByNombreContainsAndEstado(page, nombre, Estado.ACTIVO);
    }



    public Evento agregar(EventoDTO eventoDTO) {

        ModelMapper modelMapper = new ModelMapper();
        Evento evento = modelMapper.map(eventoDTO, Evento.class);


        Lugar lugar = lugarServicio.agregar(eventoDTO.getLugar());
        evento.setLugar(lugar);


        evento.setEstado(Estado.ACTIVO);

        evento = eventoRepo.save(evento);


  /*      try {
            evento = eventoRepo.save(evento);
        } catch (DataIntegrityViolationException e) {
            // Manejar la excepción de violación de restricción única (rol duplicado)
            throw new IllegalArgumentException("El nombre del participante ya existe en la base de datos.");
        }*/
        return evento;
    }


    public Evento actualizar(EventoDTO eventoDTO, long id) {
        Optional<Evento> optionalEvento = eventoRepo.findById(id);
        if (!optionalEvento.isPresent()) {
            throw new EntityNotFoundException("Evento no encontrado");
        }
        Evento eventoUpdate = optionalEvento.get();

//        ModelMapper modelMapper = new ModelMapper();
//        eventoUpdate = modelMapper.map(eventoDTO, Evento.class);

        eventoUpdate.setLugar(lugarServicio.actualizar(eventoDTO.getLugar(), eventoUpdate.getLugar().getId()));

        eventoUpdate.setNombre(eventoDTO.getNombre());
        eventoUpdate.setFechaInicio(eventoDTO.getFechaInicio());
        eventoUpdate.setFechaFin(eventoDTO.getFechaFin());
        eventoUpdate.setDescripcion(eventoDTO.getDescripcion());
        eventoUpdate.setLinkInscripcion(eventoDTO.getLinkInscripcion());
        eventoUpdate.setEstadoEvento(eventoDTO.getEstadoEvento());
        eventoUpdate.setModalidad(eventoDTO.getModalidad());


        eventoUpdate = eventoRepo.save(eventoUpdate);

        return eventoUpdate;
    }


    public void eliminar(long id) {

        Optional<Evento> optionalParticipante = eventoRepo.findById(id);
        if (!optionalParticipante.isPresent() || optionalParticipante.get().getEstado() == Estado.ELIMINADO) {
            throw new EntityNotFoundException("Participante no encontrado");
        }

        Evento evento = optionalParticipante.get();

        evento.setEstado(Estado.ELIMINADO);

        eventoRepo.save(evento);
    }

}
