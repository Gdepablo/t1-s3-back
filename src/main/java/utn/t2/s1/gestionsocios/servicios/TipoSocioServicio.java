package utn.t2.s1.gestionsocios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.excepciones.CategoriaException;
import utn.t2.s1.gestionsocios.excepciones.TipoException;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.TipoSocioRepo;

import java.util.List;

@Service
public class TipoSocioServicio {
    @Autowired
    TipoSocioRepo repo;

    public TipoSocio buscarPorNombre(String nombre) throws TipoException{
        TipoSocio tipo = repo.findByNombreAndEstado(nombre,Estado.ACTIVO);
        if (tipo == null){
            throw new TipoException();
        }else{
            return tipo;
        }
    }
    public List<TipoSocio> tiposDesocio(){
        return repo.findAllByEstado(Estado.ACTIVO);
    }

    public List<String> nombresSocios(){
        return repo.findAllByEstado(Estado.ACTIVO).stream().map(TipoSocio::getNombre).toList() ;
    }
}
