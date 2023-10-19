package utn.t2.s1.gestionsocios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.SocioRepo;

@Service
public class SocioServicio {
    @Autowired
    private SocioRepo repo;


    public Socio buscarPorId(Long id) {
        return repo.findByIdAndEstado(id, Estado.ACTIVO);
    }

    public Page<Socio> buscarTodos(Pageable pageable) {

        return repo.findAllByEstado(pageable, Estado.ACTIVO);
    }



    public Page<Socio> buscarPorDenominacionYFiltrado(Pageable pageable, String denominacion, String tipo) {

        if (denominacion != null && tipo != null){
            return repo.findByDenominacionContainingAndTipo_NombreAndTipo_EstadoAndEstado(pageable, denominacion, tipo, Estado.ACTIVO, Estado.ACTIVO);
        } else if (denominacion == null && tipo != null){
            return repo.findByTipo_NombreAndTipo_EstadoAndEstado(pageable, tipo, Estado.ACTIVO, Estado.ACTIVO);
        } else if (denominacion != null && tipo == null){
            return repo.findByDenominacionContainingAndEstado(pageable, denominacion, Estado.ACTIVO);
        } else {
            return repo.findAllByEstado(pageable, Estado.ACTIVO);
        }


    }


    public Socio agregar(Socio socio) {
        return repo.save(socio);
    }

    public void borrar(Long id) {
        Socio _socio = this.buscarPorId(id);
        _socio.setEstado(Estado.ELIMINADO);
        repo.save(_socio);

    }

    public Socio modificar(Long id, Socio socio) {
        //TODO chequear si es nulo
        socio.setId(id);
        return repo.save(socio);
    }

}
