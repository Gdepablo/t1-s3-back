//package utn.t2.s1.gestionsocios.servicios;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Service;
//import utn.t2.s1.gestionsocios.converters.RolConverter;
//import utn.t2.s1.gestionsocios.converters.TipoDeUsuarioConverter;
//import utn.t2.s1.gestionsocios.dtos.RolDTO;
//import utn.t2.s1.gestionsocios.dtos.TipoDeUsuarioDTO;
//import utn.t2.s1.gestionsocios.excepciones.CategoriaException;
//import utn.t2.s1.gestionsocios.excepciones.RolException;
//import utn.t2.s1.gestionsocios.modelos.Categoria;
//import utn.t2.s1.gestionsocios.modelos.Rol;
//import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;
//import utn.t2.s1.gestionsocios.persistencia.Estado;
//import utn.t2.s1.gestionsocios.repositorios.CategoriaRepo;
//import utn.t2.s1.gestionsocios.repositorios.RolRepo;
//import utn.t2.s1.gestionsocios.repositorios.TipoDeUsuarioRepo;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class RolServicio {
//
//    @Autowired
//    RolRepo rolRepo;
//
//    @Autowired
//    RolConverter rolConverter;
//
//
//    public List<String> buscarTodos() {
//        return rolRepo.findAllByEstado(Estado.ACTIVO).stream().map(Rol::getNombreRol).toList() ;
//    }
//
//
//    public Rol buscarPorNombre(String nombre) {
//        return rolRepo.findByNombreAndEstado(nombre, Estado.ACTIVO);
//    }
//
//
//    public Rol agregar(RolDTO rolDTO) {
//        Rol rol = rolConverter.toRol(rolDTO);
//
//        rol.setEstado(Estado.ACTIVO);
//
//        try {
//            rol = rolRepo.save(rol);
//        } catch (DataIntegrityViolationException e) {
//            // Manejar la excepción de violación de restricción única (rol duplicado)
//            throw new IllegalArgumentException("El nombre del rol ya existe en la base de datos.");
//        }
//        return rol;
//    }
//
//
//
//
//    public Rol actualizar(RolDTO rolDTO, long id) {
//        Optional<Rol> optionalRol = rolRepo.findById(id);
//        if (!optionalRol.isPresent()) {
//            throw new RuntimeException("Tipo de usuario no encontrado");
//        }
//        Rol rolUpdate = optionalRol.get();
//        rolUpdate.setNombreRol(rolDTO.getNombre());
//        rolUpdate = rolRepo.save(rolUpdate);
//
//        return rolUpdate;
//    }
//
//
//    public void eliminar(long id) {
//
//        Optional<Rol> optionalRol = rolRepo.findById(id);
//        if (!optionalRol.isPresent() || optionalRol.get().getEstado() == Estado.ELIMINADO) {
//            throw new RuntimeException("ROL no encontrado");
//        }
//
//        Rol rol = optionalRol.get();
//
//        rol.setEstado(Estado.ELIMINADO);
//
//        rolRepo.save(rol);
//    }
//
//}
