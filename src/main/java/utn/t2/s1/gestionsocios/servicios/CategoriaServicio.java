package utn.t2.s1.gestionsocios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.repositorios.CategoriaRepo;

import java.util.List;

@Service
public class CategoriaServicio {
    @Autowired
    private CategoriaRepo repo;

    public Categoria buscarPorId(long id ){
        return repo.findById(id).orElse(null);
    }
    public List<Categoria> buscarTodas(){
        return (List<Categoria>) repo.findAll();
    }
    public Categoria agregar(Categoria categoria){
        return repo.save(categoria);
    }
    public Categoria modificar(Long id,Categoria categoria){
        categoria.setId(id);
        return repo.save(categoria);
    }
    public void borrar(Long id){
        repo.delete(this.buscarPorId(id));
    }
}
