package utn.t2.s1.gestionsocios.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.servicios.SocioServicio;

import java.util.List;

@RestController
@RequestMapping("/socios")
public class SocioController {

    @Autowired
    SocioServicio servicio;

    @GetMapping()
    public List<Socio> verSocios(){
        return servicio.buscarTodos();
    }

    @GetMapping("/{id}")
    public Socio verSocio(@PathVariable Long id) {
        return servicio.buscarPorId(id);
    }

    @PostMapping()
    public String agregarSocio(@RequestBody Socio socio){
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

}
