package ar.com.ada.api.aladas.controllers;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.Service.AeropuertoService;
import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.models.response.GenericResponse;

@RestController
public class AeropuertoController {
    @Autowired
    AeropuertoService service;

    @PostMapping("/api/aeropuertos")
    public ResponseEntity<GenericResponse> crear(@RequestBody Aeropuerto aeropuerto) {

        GenericResponse respuesta = new GenericResponse();

        service.crear(aeropuerto.getAeropuertoId(), aeropuerto.getNombre(), aeropuerto.getCodigoIATA());
      //  if (aeropuerto.getAeropuertoId()== service.existeId(aeropuerto))

        respuesta.isOk = true;
        respuesta.message = "Se creo correctamente";
        respuesta.id = aeropuerto.getAeropuertoId();

        return ResponseEntity.ok(respuesta);

    }
}
