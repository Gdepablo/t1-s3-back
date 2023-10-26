//package utn.t2.s1.gestionsocios.repositorios;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import utn.t2.s1.gestionsocios.modelos.AutoridadDepartamento;
//import utn.t2.s1.gestionsocios.persistencia.Estado;
//import utn.t2.s1.gestionsocios.persistencia.Persistente;
//
//import java.io.Serializable;
//import java.util.Optional;
//
//public interface AutoridadRepo<E extends Persistente, I extends Serializable> extends JpaRepository<E, I> {
//
//
//    Page<E> findAllByEstado(Pageable pageable, Estado estado);
//
//    Optional<E> findByIdAndEstado(Long id, Estado estado);
//
//
//
//
//}
