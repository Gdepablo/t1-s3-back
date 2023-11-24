package utn.t2.s1.gestionsocios.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utn.t2.s1.gestionsocios.converters.SocioConverter;
import utn.t2.s1.gestionsocios.dtos.DepartamentoDTO;
import utn.t2.s1.gestionsocios.dtos.SocioDTO;
import utn.t2.s1.gestionsocios.excepciones.CategoriaException;
import utn.t2.s1.gestionsocios.excepciones.TipoException;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.servicios.CategoriaServicio;
import utn.t2.s1.gestionsocios.servicios.LogoServicio;
import utn.t2.s1.gestionsocios.servicios.SocioServicio;
import utn.t2.s1.gestionsocios.servicios.TipoSocioServicio;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Tag(name = "Operaciones para los socios", description = "Api para realizar las operaciones de alta, baja y modificacion de un socio")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@RequestMapping("/socios")
@Validated
@CrossOrigin
public class SocioController {

    @Autowired
    SocioServicio servicio;
    @Autowired
    CategoriaServicio categoriaServicio;
    @Autowired
    TipoSocioServicio tipoServicio;
    @Autowired
    SocioConverter socioConverter;

    @Autowired
    LogoServicio logoServicio;


    @GetMapping()
    @Operation(summary = "Retorna todos los socios de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socios encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Socio.class)) }),
    })
    public ResponseEntity<Page<Socio>> verSocios(Pageable pageable){
        return new ResponseEntity<>(servicio.buscarTodos(pageable), HttpStatus.OK);
    }



    @GetMapping(value = {"/search", "/search/"})
    @Operation(summary = "Retorna los socios filtrados y buscados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socios Filtrados encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Socio.class)) }),
    })
    public ResponseEntity<Page<Socio>> verSociosFiltradoBuscado(Pageable pageable, @RequestParam(required = false) String denominacion, @RequestParam(required = false) String tipo){

        Page<Socio> socios = servicio.buscarPorDenominacionYFiltrado(pageable, denominacion, tipo);
        return new ResponseEntity<>(socios, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    @Operation(summary = "Retorna el socio correspondiente al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio encontrado" ,content = { @Content(mediaType = "application/json",schema = @Schema( implementation = Socio.class)) }),
            @ApiResponse(responseCode = "404", description = "El socio no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<Socio> verSocio(@PathVariable Long id) {

        if (servicio.buscarPorId(id) == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Socio _socio = servicio.buscarPorId(id);
        return new ResponseEntity<>(_socio, HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Inserta un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio creado" ,content = { @Content(mediaType = "application/json",schema = @Schema( implementation = Socio.class)) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "La categoria no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El tipo no fue encontrada",content = { @Content(schema = @Schema()) }),

    })

//    public ResponseEntity<Object> agregarSocio(@RequestBody @Valid SocioDTO socioDTO){ //TODO DTO socio
    public ResponseEntity<Object> agregarSocio(@RequestParam(value = "logo", required = false) MultipartFile logo, @Valid @RequestParam("socio") String socioString) { //TODO DTO socio


        File file = null;

        try {
            String url;
            if (logo != null && !logo.isEmpty()) { // si hay un logo, entonces hace esto
                //return new ResponseEntity<>("Logo vacio", HttpStatus.UNPROCESSABLE_ENTITY);
                url = logoServicio.save(logo);


                // Encontrar el índice de "logo/"
                int i = url.indexOf("logo/");
                // Extraer la subcadena desde el índice i + 5 (para saltar el "logo/")
                String sub = url.substring(i + 5);
                
                file = new File("./uploads/" + sub);

            } else {
                url = null;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            SocioDTO socioDTO = objectMapper.readValue(socioString, SocioDTO.class);

            socioDTO.setLogo(url);


//            Socio socio = servicio.agregar(socioDTO);


            Set<Categoria> categorias = null;
            TipoSocio tipo = null;
            try {
                categorias = categoriaServicio.stringSetToCategoriaSet(socioDTO.getCategorias());
            } catch (CategoriaException e) {
                boolean borrado = file.delete();
                return new ResponseEntity<>("Una de las categorias no existe en la base de datos", HttpStatus.NOT_FOUND);
            }
            tipo = tipoServicio.buscarPorNombre(socioDTO.getTipo());
            if (tipo == null) {
                boolean borrado = file.delete();
                return new ResponseEntity<>("El tipo no existe en la base de datos", HttpStatus.NOT_FOUND);
            }

            Socio _socio = socioConverter.toSocio(socioDTO, categorias, tipo);
            _socio.setEstado(Estado.ACTIVO);


            Optional<Socio> _socioOpcional = servicio.buscarPorNombre(socioDTO.getDenominacion());

            if (_socioOpcional.isEmpty()) {
                return new ResponseEntity<>(servicio.agregar(_socio), HttpStatus.CREATED);
            }

            boolean borrado = file.delete();
            return new ResponseEntity<>("La denominacion de socio ya existe", HttpStatus.BAD_REQUEST);


        }catch (ConstraintViolationException e) {
            boolean borrado = file.delete();
            String mensaje = "";
            // Recorremos el conjunto de violaciones y mostramos solo el mensaje
            for (ConstraintViolation violation : e.getConstraintViolations()) {
                mensaje += violation.getPropertyPath() + " " + violation.getMessage() + "\n";
            }
            return new ResponseEntity<>(mensaje, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (NullPointerException e) {
            boolean borrado = file.delete();
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            boolean borrado = file.delete();
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }

//        } catch (JsonMappingException e) {
//            throw new RuntimeException(e);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return new ResponseEntity<>("La denominacion de socio ya existe", HttpStatus.BAD_REQUEST);
    }





    @PutMapping("/{idSocio}")
    @Operation(summary = "Modifica un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El socio no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "La categoria no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El tipo no fue encontrada",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<Object> modificarSocio( @Valid @PathVariable Long idSocio,  @RequestParam(value = "logo", required = false) MultipartFile logo,@Valid @RequestParam("socio") String socioString){

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            SocioDTO socioDTO = objectMapper.readValue(socioString, SocioDTO.class);


            String url = servicio.buscarPorId(idSocio).getLogo();

            if( logo != null && !logo.isEmpty()){ // si hay un logo, entonces hace esto
                // Borra el logo viejo a partir del userID
                logoServicio.deletePorSocio(idSocio);

                System.out.println("Logo viejo borradoOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

                url = logoServicio.save(logo);

            }

            socioDTO.setLogo(url);
//            Socio socio = servicio.modificar(idSocio, socioDTO);
            if (servicio.buscarPorId(idSocio) == null){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); //TODO ver si se devuelve nulo o una cadena de texto
            }

            Set<Categoria> categorias = null;
            TipoSocio tipo = null;
            try {
                categorias = categoriaServicio.stringSetToCategoriaSet(socioDTO.getCategorias());
            } catch (CategoriaException e) {
                return new ResponseEntity<>("Una de las categorias no existe en la base de datos", HttpStatus.NOT_FOUND);
            }
            tipo = tipoServicio.buscarPorNombre(socioDTO.getTipo());
            if(tipo == null){
                return new ResponseEntity<>("El tipo no existe en la base de datos", HttpStatus.NOT_FOUND);
            }

            Socio _socio = socioConverter.toSocio(socioDTO,categorias, tipo);
            _socio.setEstado(Estado.ACTIVO);

            servicio.modificar(idSocio,_socio);

            return new ResponseEntity<>(_socio, HttpStatus.CREATED);

        } catch (ConstraintViolationException e) {
            String mensaje = "";
            // Recorremos el conjunto de violaciones y mostramos solo el mensaje
            for (ConstraintViolation violation : e.getConstraintViolations()) {
                mensaje += violation.getPropertyPath() + " " + violation.getMessage() + "\n";

            }
            return new ResponseEntity<>(mensaje, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }

//        if (servicio.buscarPorId(id) == null){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); //TODO ver si se devuelve nulo o una cadena de texto
//        }
//        Set<Categoria> categorias = null;
//        TipoSocio tipo = null;
//        try {
//            categorias = categoriaServicio.stringSetToCategoriaSet(socioDTO.getCategorias());
//        } catch (CategoriaException e) {
//            return new ResponseEntity<>("Una de las categorias no existe en la base de datos", HttpStatus.NOT_FOUND);
//        }
//        tipo = tipoServicio.buscarPorNombre(socioDTO.getTipo());
//        if(tipo == null){
//            return new ResponseEntity<>("El tipo no existe en la base de datos", HttpStatus.NOT_FOUND);
//        }
//
//        Socio _socio = socioConverter.toSocio(socioDTO,categorias, tipo);
//        _socio.setEstado(Estado.ACTIVO);
//
//        servicio.modificar(id,_socio);
//
//        return new ResponseEntity<>(_socio, HttpStatus.CREATED);
    }






//        @PutMapping("/{id}")
//    @Operation(summary = "Modifica un socio en la Base de datos")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Socio modificado" ,content = { @Content(schema = @Schema()) }),
//            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
//            @ApiResponse(responseCode = "404", description = "El socio no fue encontrada",content = { @Content(schema = @Schema()) }),
//            @ApiResponse(responseCode = "404", description = "La categoria no fue encontrada",content = { @Content(schema = @Schema()) }),
//            @ApiResponse(responseCode = "404", description = "El tipo no fue encontrada",content = { @Content(schema = @Schema()) }),
//    })
//    public ResponseEntity<Object> modificarSocio( @PathVariable Long id, @RequestBody @Valid SocioDTO socioDTO){
//
//        if (servicio.buscarPorId(id) == null){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); //TODO ver si se devuelve nulo o una cadena de texto
//        }
//        Set<Categoria> categorias = null;
//        TipoSocio tipo = null;
//        try {
//            categorias = categoriaServicio.stringSetToCategoriaSet(socioDTO.getCategorias());
//        } catch (CategoriaException e) {
//            return new ResponseEntity<>("Una de las categorias no existe en la base de datos", HttpStatus.NOT_FOUND);
//        }
//        tipo = tipoServicio.buscarPorNombre(socioDTO.getTipo());
//        if(tipo == null){
//            return new ResponseEntity<>("El tipo no existe en la base de datos", HttpStatus.NOT_FOUND);
//        }
//
//        Socio _socio = socioConverter.toSocio(socioDTO,categorias, tipo);
//        _socio.setEstado(Estado.ACTIVO);
//
//        servicio.modificar(id,_socio);
//
//        return new ResponseEntity<>(_socio, HttpStatus.CREATED);
//    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio eliminado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El socio no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<String> eliminarSocio(@PathVariable Long id){
        servicio.borrar(id);
        return new ResponseEntity<>("Socio eliminado", HttpStatus.OK);
    }


}
