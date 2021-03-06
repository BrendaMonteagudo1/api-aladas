package ar.com.ada.api.aladas.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Pasaje;
import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.repos.PasajeRepository;
import ar.com.ada.api.aladas.sistema.comm.EmailService;

@Service
public class PasajeService {

    @Autowired
    PasajeRepository repo;

    @Autowired
    ReservaService resService;

    @Autowired
    VueloService vueloService;

    @Autowired
    EmailService emailService;

    public Pasaje emitir(Integer reservaId) {

        Pasaje pasaje = new Pasaje();
        pasaje.setFechaEmision(new Date());

        Reserva reserva = resService.buscarPorId(reservaId);
        reserva.setEstadoReservaId(EstadoReservaEnum.EMITIDA);
        reserva.asociarPasaje(pasaje);
        Integer nuevaCapacidad = reserva.getVuelo().getCapacidad() - 1;
        reserva.getVuelo().setCapacidad(nuevaCapacidad);

       if (nuevaCapacidad.equals(0)){
          
         reserva.getVuelo().setEstadoVueloId(EstadoVueloEnum.CERRADO);

        }

        /* problema concurrencia
         * "update vuelo set capacidad = 29 where vueloid = 99 and capacidad = 30"
         * 
         * "update vuelo set capacidad = 29 where vueloid = 99 and capacidad = 30"
         */

        vueloService.actualizar(reserva.getVuelo());
        // reservaService.actualizar(reserva);
        // pasajeService.actualizar(pasa);
      
        return pasaje;

    }

}
