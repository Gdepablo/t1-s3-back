package utn.t2.s1.gestionsocios.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.t2.s1.gestionsocios.dtos.SocioDTO;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import utn.t2.s1.gestionsocios.servicios.SocioServicio;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocioControllerTest {
    @InjectMocks
    private SocioController socioController;
    @Mock
    private SocioServicio servicio;

    private List<Socio> sociosLista;


    @BeforeEach
    void setUpSocios(){
        Socio socio1 = new Socio(
                1L, // ID
                "NombreSocio1", // denominacion
                "123456789", // telefono
                TipoSocio.CAMARA, // tipo
                "correo@socio.com", // mail
                "Descripción del socio", // descripcion
                "http://www.socio.com", // web
                LocalDate.of(2023, 9, 27), // fechaAlta (por ejemplo, hoy)
                "ruta/logo.png", // logo
                new HashSet<>(Arrays.asList(Categoria.AMBIENTE, Categoria.FISCAL)) // categorias
        );

        Socio socio2 = new Socio(
                2L, // ID
                "Otro Socio", // denominacion
                "987654321", // telefono
                TipoSocio.EMPRESA, // tipo
                "otrocorreo@socio.com", // mail
                "Otra descripción del socio", // descripcion
                "http://www.otrosocio.com", // web
                LocalDate.of(2023, 8, 15), // fechaAlta (por ejemplo, otra fecha)
                "ruta/otrosocio.png", // logo
                new HashSet<>(Arrays.asList(Categoria.EMPRESAS, Categoria.ENVASES)) // categorias (diferentes categorías)
        );

       List<Socio> sociosPrueba = Arrays.asList(socio1,socio2);
       this.sociosLista=sociosPrueba;

    }

    @Test
    @DisplayName("Retornar lista de socios al realizar el GET a /socios")
    void verSocios() {
        when(servicio.buscarTodos()).thenReturn(this.sociosLista);
        ResponseEntity<List<Socio>> respuesta=socioController.verSocios();
        verify(servicio).buscarTodos();//Utilizo verify porque es esencial para el test

        //Verifico StatusCode
        assertEquals(HttpStatus.OK,respuesta.getStatusCode());
        //Verifico contenido
        List<Socio> sociosObtenidos=respuesta.getBody();
        assertNotNull(sociosObtenidos);
        assertEquals(2,sociosObtenidos.size());

    }

    @ParameterizedTest(name="buscando al ID con valor: {0}")
    @CsvSource({"1,0","2,1","3,2"})
    //@ValueSource(longs = {1L,2L,3L}, ints ={1,2,3})
    @DisplayName("Buscar socio según su id")
    void verSocio(long id, int iteraciones) {
        Socio socioEsperado;

        if (sociosLista.size()>iteraciones){
            socioEsperado= sociosLista.get(iteraciones);
        }
        else{
            socioEsperado=null;
        }

        when(servicio.buscarPorId(id)).thenAnswer(invocation -> {
            Long idBuscado = invocation.getArgument(0);//le paso el ID solicitado
            Socio socioEncontrado = sociosLista.stream()
                    .filter(socio -> socio.getId().equals(idBuscado))
                    .findFirst()
                    .orElse(null);
            return socioEncontrado;
        });

        ResponseEntity<Socio> socioRespuesta = socioController.verSocio(id);

        if (socioEsperado!=null) {
            assertEquals(HttpStatus.OK, socioRespuesta.getStatusCode());
            assertEquals(socioEsperado, socioRespuesta.getBody());
        }
        else {
            assertEquals(HttpStatus.NOT_FOUND,socioRespuesta.getStatusCode());
            assertNull(socioRespuesta.getBody());
        }

    }

    @Test
    @DisplayName("Agregar socio nuevo y devolver 201")
    void agregarSocioNuevo() {
        SocioDTO socioDTO= new SocioDTO();

        socioDTO.setDenominacion("NuevoSocio");
        socioDTO.setTelefono("1125987954");
        socioDTO.setTipo(TipoSocio.CAMARA);
        socioDTO.setMail("nuevosocio@socio.com");
        socioDTO.setDescripcion("Nueva descripción");
        socioDTO.setWeb("http://www.nuevosocio.com");
        socioDTO.setFechaAlta(LocalDate.of(2023,8,15));
        socioDTO.setLogo("ruta/nuevosocio.png");
        socioDTO.setCategorias(new HashSet<>(Arrays.asList(Categoria.CAMARAS, Categoria.NORMATIVA)));

        Socio nuevoSocio = socioDTO.toSocio();
        nuevoSocio.setId(3L);

        when(servicio.agregar(any(Socio.class))).thenReturn(nuevoSocio);

        ResponseEntity<Socio>respuesta=socioController.agregarSocio(socioDTO);

        assertEquals(HttpStatus.CREATED,respuesta.getStatusCode());
        assertEquals(nuevoSocio.getLogo(),respuesta.getBody().getLogo());
        assertEquals(nuevoSocio.getDenominacion(), respuesta.getBody().getDenominacion());
        assertEquals(nuevoSocio.getTelefono(), respuesta.getBody().getTelefono());
        assertEquals(nuevoSocio.getTipo(), respuesta.getBody().getTipo());
        assertEquals(nuevoSocio.getMail(), respuesta.getBody().getMail());
        assertEquals(nuevoSocio.getDescripcion(), respuesta.getBody().getDescripcion());
        assertEquals(nuevoSocio.getWeb(), respuesta.getBody().getWeb());
        assertEquals(nuevoSocio.getFechaAlta(), respuesta.getBody().getFechaAlta());
        assertEquals(nuevoSocio.getLogo(), respuesta.getBody().getLogo());
        assertEquals(nuevoSocio.getCategorias(), respuesta.getBody().getCategorias());

    }

    @Test
    @Disabled
    @DisplayName("Agregar socio inválido y devolver 400")
    void agregarSocioCamposVacios() {
        SocioDTO socioDTO= new SocioDTO();//Socio vacio
        Socio nuevoSocio = socioDTO.toSocio();

        when(servicio.agregar(any(Socio.class))).thenReturn(nuevoSocio);

        ResponseEntity<Socio>respuesta=socioController.agregarSocio(socioDTO);

        assertNull(socioDTO.getLogo());
        assertNull(socioDTO.getMail());
        assertNull(socioDTO.getTipo());
        assertNull(socioDTO.getDescripcion());
        assertNull(socioDTO.getTelefono());
        assertNull(socioDTO.getCategorias());
        assertNull(socioDTO.getWeb());
        assertNull(socioDTO.getFechaAlta());
        assertNull(socioDTO.getDenominacion());

        assertEquals(HttpStatus.BAD_REQUEST,respuesta.getStatusCode());

    }

    @Test
    @Disabled
    @DisplayName("Agregar socio con ID duplicado y devolver 409")
    void agregarSocioIdDuplicado() {
        SocioDTO socioDTO= new SocioDTO();//Socio vacio

        socioDTO.setDenominacion("NuevoSocio");
        socioDTO.setTelefono("1125987954");
        socioDTO.setTipo(TipoSocio.CAMARA);
        socioDTO.setMail("nuevosocio@socio.com");
        socioDTO.setDescripcion("Nueva descripción");
        socioDTO.setWeb("http://www.nuevosocio.com");
        socioDTO.setFechaAlta(LocalDate.of(2023,8,15));
        socioDTO.setLogo("ruta/nuevosocio.png");
        socioDTO.setCategorias(new HashSet<>(Arrays.asList(Categoria.CAMARAS, Categoria.NORMATIVA)));

        Socio nuevoSocio = socioDTO.toSocio();
        nuevoSocio.setId(2L);

        when(servicio.agregar(any(Socio.class))).thenReturn(nuevoSocio);

        ResponseEntity<Socio>respuesta=socioController.agregarSocio(socioDTO);

        assertEquals(HttpStatus.CONFLICT,respuesta.getStatusCode());//También podría ser BAD_REQUEST

    }
    @Test
    void modificarSocio() {
    }

    @Test
    void eliminarSocio() {
    }
}