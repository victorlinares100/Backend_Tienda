package com.example.elarayax.naves.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.elarayax.naves.model.DetalleComprobante;
import java.util.List;


public interface DetalleComprobanteRepository extends JpaRepository<DetalleComprobante, Long> {
	
    List<DetalleComprobante> findByComprobanteId(Long comprobanteId);

}
