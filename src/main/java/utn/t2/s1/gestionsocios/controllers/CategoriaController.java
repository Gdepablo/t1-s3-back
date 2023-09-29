package utn.t2.s1.gestionsocios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.servicios.CategoriaServicio;

@RestController
@RequestMapping("/categorias")
@Validated
@CrossOrigin
public class CategoriaController {
    @Autowired
    CategoriaServicio categoriaServicio;
    @GetMapping("/lista")
    public ResponseEntity<Object> verNombresCategorias(){
        return new ResponseEntity<>(categoriaServicio.nombresCategoria(), HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<Object> verCategorias(){
        return new ResponseEntity<>(categoriaServicio.categorias(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Object> agregarCategoria(){ //TODO
        return new ResponseEntity<>(null,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object>modificarCategoria(@PathVariable Long id){ //TODO
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarCategoria(@PathVariable Long id){ //TODO
        return new ResponseEntity<>(null,HttpStatus.OK);
    }
}
