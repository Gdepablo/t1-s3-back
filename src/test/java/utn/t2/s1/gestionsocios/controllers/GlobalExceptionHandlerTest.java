package utn.t2.s1.gestionsocios.controllers;

import jdk.swing.interop.SwingInterOpUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    private GlobalExceptionHandler controller;
    @Mock
    private MethodArgumentNotValidException exception;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpMessageNotReadableException deserializationException;

    @BeforeEach
    void setUp(){
        controller= new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Devolver exception, con su respectiva key")
    void handleValidationExceptions() {

        //Lista de errores que se obtiene de BindingResult
        List<FieldError> errores = new ArrayList<>();
        errores.add(new FieldError("modelo","campo1","error1"));
        errores.add(new FieldError("modelo","campo2","error2"));

        when(exception.getBindingResult()).thenReturn(bindingResult);
        //when(bindingResult.getAllErrors()).thenReturn(errores);
        doReturn(errores).when(bindingResult).getAllErrors();

        Map<String, String> resultado= controller.handleValidationExceptions(exception);

        assertEquals(2,resultado.size(),"Debe haber 2 errores");
        assertEquals("error1",resultado.get("campo1"),"Debe coincidir con la key del map");
        assertEquals("error2",resultado.get("campo2"),"Debe coincidir con la key del map");

    }

    @DisplayName("Status code y Mensaje de error en la deserialización del JSON.")
    @Test
    void handleJsonDeserializationException() {
        String expectedErrorMessage = "Error en la deserialización del JSON: MENSAJE";
        when(deserializationException.getRootCause()).thenReturn(new RuntimeException("MENSAJE"));

        ResponseEntity<String> responseEntity= controller.handleJsonDeserializationException(deserializationException);

        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode(),"Satus Code: 400 (BAD_REQUEST)");
        assertEquals(expectedErrorMessage,responseEntity.getBody(),"Mensaje de error deben coincidir");

    }
}