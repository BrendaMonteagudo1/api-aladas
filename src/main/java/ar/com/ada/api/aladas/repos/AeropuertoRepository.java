package ar.com.ada.api.aladas.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.aladas.entities.Aeropuerto;

@Repository
public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Integer> {
    Aeropuerto findByCodigoIATA(String codigoIATA);
    
    Aeropuerto findByAeropuertoId(Integer aeropuertoId);
}
