package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.dtos.ParticipanteDTO;
import utn.t2.s1.gestionsocios.modelos.Participante;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.ParticipanteRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipanteServicio {

    @Autowired
    private ParticipanteRepo participanteRepo;



    public Page<Participante> buscarTodos(Pageable page) {
        return participanteRepo.findAllByEstado(page , Estado.ACTIVO) ;
    }


    public Participante buscarPorNombre(String nombre) {
        return participanteRepo.findByNombreAndEstado(nombre, Estado.ACTIVO);
    }


    public Participante agregar(ParticipanteDTO participanteDTO) {

        ModelMapper modelMapper = new ModelMapper();
        Participante participante = modelMapper.map(participanteDTO, Participante.class);

        participante.setEstado(Estado.ACTIVO);

        try {
            participante = participanteRepo.save(participante);
        } catch (DataIntegrityViolationException e) {
            // Manejar la excepción de violación de restricción única (rol duplicado)
            throw new IllegalArgumentException("El nombre del participante ya existe en la base de datos.");
        }
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
