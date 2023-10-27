package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.dtos.DepartamentoDTO;
import utn.t2.s1.gestionsocios.dtos.SubDepartamentoDTO;
import utn.t2.s1.gestionsocios.excepciones.DepartamentoException;
import utn.t2.s1.gestionsocios.excepciones.SubDepartamentoException;
import utn.t2.s1.gestionsocios.modelos.Departamento;
import utn.t2.s1.gestionsocios.modelos.SubDepartamento;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.DepartamentoRepo;
import utn.t2.s1.gestionsocios.repositorios.SubDepartamentoRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartamentoServicio {

    @Autowired
    private DepartamentoRepo departamentoRepo;


    public Page<Departamento> traerDepartamentos(Pageable pageable){
        Page<Departamento> departamentoPage = departamentoRepo.findAllByEstado(pageable, Estado.ACTIVO);
        departamentoPage.stream().map(this::filtrarSubDepartamentosActivos).collect(Collectors.toList());
        return departamentoPage;
    }

    public Departamento buscarPorId(Long id) throws DepartamentoException {
        Departamento departamento = departamentoRepo.findByIdAndEstado(id, Estado.ACTIVO).orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado"));
        return filtrarSubDepartamentosActivos(departamento);
    }

    public Departamento filtrarSubDepartamentosActivos(Departamento departamento){
        List<SubDepartamento> subDepartamentoList = departamento.getSubDepartamentoList().stream()
                .filter((y) -> y.getEstado().equals(Estado.ACTIVO))
                .collect(Collectors.toList());
        departamento.setSubDepartamentoList(subDepartamentoList);
        return departamento;

    }



    public Departamento agregar(DepartamentoDTO departamentoDTO){


        Departamento departamento = new Departamento();
        departamento.setNombreDepartamento(departamentoDTO.getNombre());
        departamento.setObjetivo(departamentoDTO.getObjetivo());
        departamento.setEstado(Estado.ACTIVO);

        return departamentoRepo.save(departamento);
    }



    public Page<Departamento> buscarPorNombre(Pageable pageable, String nombreUsuario){
        return departamentoRepo.findByNombreDepartamentoContainsAndEstado(pageable, nombreUsuario, Estado.ACTIVO);
    }

    public void eliminarDepartamento(Long id) throws DepartamentoException{
        Departamento departamento = this.buscarPorId(id);
        departamento.setEstado(Estado.ELIMINADO);
        departamentoRepo.save(departamento);
    }

    public Departamento actualizar(Long id, DepartamentoDTO departamentoDTO) {

        Optional<Departamento> optionalDepartamento = departamentoRepo.findByIdAndEstado(id, Estado.ACTIVO);
        if (!optionalDepartamento.isPresent()) {
            throw new RuntimeException("SubDepartamento no encontrado");
        }

        Departamento departamentoUpdate = optionalDepartamento.get();
        departamentoUpdate.setNombreDepartamento(departamentoDTO.getNombre());
        departamentoUpdate.setObjetivo(departamentoDTO.getObjetivo());

        return departamentoRepo.save(departamentoUpdate);
    }


}
