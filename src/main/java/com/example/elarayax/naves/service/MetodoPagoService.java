package com.example.elarayax.naves.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.elarayax.naves.model.MetodoPago;
import com.example.elarayax.naves.repository.MetodoPagoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPago> findAll() {
        return metodoPagoRepository.findAll();
    }

    public MetodoPago findById(Long id) {
        return metodoPagoRepository.findById(id).orElse(null);
    }

    public MetodoPago save(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    public void delete(Long id) {
        metodoPagoRepository.deleteById(id);
    }
    public MetodoPago partialUpdate(MetodoPago metodoPago){
        MetodoPago existingMetodoPago = metodoPagoRepository.findById(metodoPago.getId()).orElse(null);
        if (existingMetodoPago != null) {
            if (metodoPago.getNombreMetodo() != null) {
                existingMetodoPago.setNombreMetodo(metodoPago.getNombreMetodo());
            }
                if (metodoPago.getDescripcion() != null) {
                existingMetodoPago.setDescripcion(metodoPago.getDescripcion());
            }
            if (metodoPago.getComision() != null) {
                existingMetodoPago.setComision(metodoPago.getComision());
            }

            return metodoPagoRepository.save(existingMetodoPago);
        }
        return null;
    }
}


