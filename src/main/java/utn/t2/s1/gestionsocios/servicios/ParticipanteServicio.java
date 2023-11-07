package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.dtos.ParticipanteDTO;
import utn.t2.s1.gestionsocios.modelos.EmpresaEvento;
import utn.t2.s1.gestionsocios.modelos.Evento;
import utn.t2.s1.gestionsocios.modelos.Lugar;
import utn.t2.s1.gestionsocios.modelos.Participante;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.ParticipanteRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipanteServicio {

    @Autowired
    private ParticipanteRepo participanteRepo;

    @Autowired
    private EmpresaEventoServicio empresaEventoServicio;

    @Autowired
    private EventoServicio eventoServicio;



    public Page<Participante> buscarTodos(Pageable page) {
        return participanteRepo.findAllByEstado(page , Estado.ACTIVO) ;
    }


    public Page<Participante> buscarPorNombre(Pageable pageable, String nombre) {
        return participanteRepo.findByNombreAndEstado(pageable, nombre, Estado.ACTIVO);
    }


    public Participante agregar(ParticipanteDTO participanteDTO) {

//        ModelMapper modelMapper = new ModelMapper();
//        Evento evento = modelMapper.map(eventoDTO, Evento.class);
//
//
//        Lugar lugar = lugarServicio.agregar(eventoDTO.getLugar());
//        evento.setLugar(lugar);
//
//
//        evento.setEstado(Estado.ACTIVO);
//
//        evento = eventoRepo.save(evento);

        ModelMapper modelMapper = new ModelMapper();
        Participante participante = modelMapper.map(participanteDTO, Participante.class);

        //Buscar evento por id
        Evento evento = eventoServicio.buscarPorId(participanteDTO.getEventoId());

        EmpresaEvento empresaEvento = empresaEventoServicio.agregar(participanteDTO.getEmpresaEvento());

        participante.setEvento(evento);
        participante.setEmpresaEvento(empresaEvento);
        participante.setEstado(Estado.ACTIVO);

        participante = participanteRepo.save(participante);


//        try {
//            participante = participanteRepo.save(participante);
//        } catch (DataIntegrityViolationException e) {
//            // Manejar la excepción de violación de restricción única (rol duplicado)
//            throw new IllegalArgumentException("El nombre del participante ya existe en la base de datos.");
//        }
        return participante;
    }


//    public Participante actualizar(ParticipanteDTO participanteDTO, long id) {
//        Optional<Participante> optionalParticipante = participanteRepo.findById(id);
//        if (!optionalParticipante.isPresent()) {
//            throw new EntityNotFoundException("Participante no encontrado");
//        }
//        Participante participanteUpdate = optionalParticipante.get();
//        participanteUpdate.setNombreRol(participanteDTO.getNombre());
//        participanteUpdate = participanteRepo.save(participanteUpdate);
//
//        return participanteUpdate;
//    }
//

    public void eliminar(long id) {

        Optional<Participante> optionalParticipante = participanteRepo.findById(id);
        if (!optionalParticipante.isPresent() || optionalParticipante.get().getEstado() == Estado.ELIMINADO) {
            throw new EntityNotFoundException("Participante no encontrado");
        }

        Participante participante = optionalParticipante.get();

        participante.setEstado(Estado.ELIMINADO);

        participanteRepo.save(participante);
    }


}
