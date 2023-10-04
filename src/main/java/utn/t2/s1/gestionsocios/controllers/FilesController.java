package utn.t2.s1.gestionsocios.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.servicios.FileStorageService;

import java.io.IOException;
@RestController
@RequestMapping("/files")
@CrossOrigin
public class FilesController {
    @Autowired
    FileStorageService fileStorageService;
    @GetMapping(value = "/{fileName:.+}",   produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        return new ResponseEntity<>(resource.getContentAsByteArray(), HttpStatus.OK);
    }
}
