package utn.t2.s1.gestionsocios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.modelos.CategoriaDeprecado;
import utn.t2.s1.gestionsocios.repositorios.CategoriaRepo;

import java.util.List;

//@Service
public class CategoriaServicio {
    /*
    @Autowired
    private CategoriaRepo repo;

    public CategoriaDeprecado buscarPorId(long id ){
        return repo.findById(id).orElse(null);
    }
    public List<CategoriaDeprecado> buscarTodas(){
        return (List<CategoriaDeprecado>) repo.findAll();
    }
    public CategoriaDeprecado agregar(CategoriaDeprecado categoria){
        return repo.save(categoria);
    }
    public CategoriaDeprecado modificar(Long id, CategoriaDeprecado categoria){
        categoria.setId(id);
        return repo.save(categoria);
    }
    public void borrar(Long id){
        repo.delete(this.buscarPorId(id));
    }
    */
}
