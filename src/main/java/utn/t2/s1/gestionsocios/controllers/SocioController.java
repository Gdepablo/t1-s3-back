package utn.t2.s1.gestionsocios.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.servicios.CategoriaServicio;
import utn.t2.s1.gestionsocios.servicios.SocioServicio;

import java.util.List;

@RestController
@RequestMapping("/socios")
@Validated
public class SocioController {

    @Autowired
    SocioServicio servicio;

    @Autowired
    CategoriaServicio categoriaServicio;

    @GetMapping()
    public List<Socio> verSocios(){
        return servicio.buscarTodos();
    }

    @GetMapping("/{id}")
    public Socio verSocio(@PathVariable Long id) {
        return servicio.buscarPorId(id);
    }

    @PostMapping()
    public String agregarSocio(@RequestBody @Valid Socio socio){
        servicio.agregar(socio);
        return "ok";
    }

    @PutMapping("/{id}")
    public String modificarSocio( @PathVariable Long id, @RequestBody Socio socio){
        servicio.modificar(id,socio);
        return "ok";
    }

    @DeleteMapping("/{id}")
    public String eliminarSocio(@PathVariable Long id){
        servicio.borrar(id);
        return "ok";
    }

    @PostMapping("/categorias")
    public String agregarCategoria(@RequestBody Categoria categoria){
        categoriaServicio.agregar(categoria);
        return "ok";
    }
    @GetMapping("/categorias/{id}")
    public Categoria buscarCategoria(@PathVariable Long id){
        return categoriaServicio.buscarPorId(id);
    }
    @GetMapping("/categorias")
    public List<Categoria> verCategorias(){
        return categoriaServicio.buscarTodas();
    }
    @DeleteMapping("/categorias/{id}")
    public String eliminarCategoria(@PathVariable Long id){
        categoriaServicio.borrar(id);
        return "ok";
    }

    @PutMapping("/categorias/{id}")
    public String modificarCategoria(@PathVariable Long id, @RequestBody Categoria categoria){
        categoriaServicio.modificar(id,categoria);
        return "ok";
    }

}
