package ar.com.ada.api.aladas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.aladas.Service.AeropuertoService;
import ar.com.ada.api.aladas.Service.VueloService.ValidacionVueloDataEnum;
import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.models.request.EstadoVueloRequest;
import ar.com.ada.api.aladas.models.response.GenericResponse;

@RestController
public class VueloController {

    /*
     * @Autowired //Version simple VueloService service;
     */

    // Version mas "pro"
    private ar.com.ada.api.aladas.Service.VueloService service;

    private ar.com.ada.api.aladas.Service.AeropuertoService aeropuertoService;

    public VueloController(ar.com.ada.api.aladas.Service.VueloService service, AeropuertoService aeropuertoService) {
        this.service = service;
        this.aeropuertoService = aeropuertoService;
    }

    @PostMapping("/api/vuelos")
    public ResponseEntity<GenericResponse> postCrearVuelo(@RequestBody Vuelo vuelo) {
        GenericResponse respuesta = new GenericResponse();

        ValidacionVueloDataEnum resultadoValidacion = service.validar(vuelo);
        if (resultadoValidacion == ValidacionVueloDataEnum.OK) {
            service.crear(vuelo);

            respuesta.isOk = true;
            respuesta.id = vuelo.getVueloId();
            respuesta.message = "Vuelo creado correctamente";

            return ResponseEntity.ok(respuesta);
        } else {

            respuesta.isOk = false;
            respuesta.message = "Error(" + resultadoValidacion.toString() + ")";

            return ResponseEntity.badRequest().body(respuesta);
        }

    }

    /*
     * @PostMapping("/api/v2/vuelos") public ResponseEntity<GenericResponse>
     * postCrearVueloV2(@RequestBody Vuelo vuelo) { GenericResponse respuesta = new
     * GenericResponse();
     * 
     * Aeropuerto ao = aeropuertoService.b
     * 
     * Vuelo vueloCreado = service.crear(vuelo.getFecha(), vuelo.getCapacidad(),
     * vuelo.getAeropuertoOrigen(), vuelo.getAeropuertoDestino(), vuelo.getPrecio(),
     * vuelo.getCodigoMoneda());
     * 
     * respuesta.isOk = true; respuesta.id = vueloCreado.getVueloId();
     * respuesta.message = "Vuelo creado correctamente";
     * 
     * return ResponseEntity.ok(respuesta); }
     */
    @PutMapping("/api/vuelos/{id}/estados")
    public ResponseEntity<GenericResponse> putActualizarEstadoVuelo(@PathVariable Integer id,
            @RequestBody EstadoVueloRequest estadoVuelo) {

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        // Pasos:
        // 1 buscar vuelo por Id y lo asignamos a una variable(vuelo).
        Vuelo vuelo = service.buscarPorId(id);
        // 2 setearle el nuevo estado, que vino en estadoVuelo al vuelo.
        vuelo.setEstadoVueloId(estadoVuelo.estado);
        // 3 grabar el vuelo en la base de datos.
        service.actualizar(vuelo);
        
        // 4 que devuelva el status final.
        r.message = "actualizado";
        return ResponseEntity.ok(r);
    }

    @GetMapping("/api/vuelos/abiertos")
    public ResponseEntity<List<Vuelo>> getVuelosAbiertos(){
        
        return ResponseEntity.ok(service.traerVuelosAbiertos());
    }

    @GetMapping("/api/vuelos/{id}")
    public ResponseEntity<Vuelo> getVueloPorId(@PathVariable Integer id){
        Vuelo vuelo = service.buscarPorId(id);
        return ResponseEntity.ok(vuelo);
    }

    @GetMapping("api/vuelos/aeropuertos_origen")
    public ResponseEntity<List<Vuelo>> getVueloPorOrigen(@PathVariable Integer aeropuerto_origen){
        List<Vuelo> vuelo = service.buscarOrigen(aeropuerto_origen);
        return ResponseEntity.ok(vuelo);
    }
    @GetMapping("api/vuelos/aeropuertos?destino=true")
    public ResponseEntity<List<Vuelo>> getVueloPorDestino(@PathVariable Integer aeropuerto_destino){
        List<Vuelo> vuelo = service.buscarDestino(aeropuerto_destino);
        return ResponseEntity.ok(vuelo);
    }

}


