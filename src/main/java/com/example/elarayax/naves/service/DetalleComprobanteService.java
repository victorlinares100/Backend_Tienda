package com.example.elarayax.naves.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.elarayax.naves.repository.DetalleComprobanteRepository;
import com.example.elarayax.naves.model.DetalleComprobante;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleComprobanteService {

    @Autowired
    private DetalleComprobanteRepository detalleComprobanteRepository;

    public List<DetalleComprobante> findAll() {
        return detalleComprobanteRepository.findAll();
    }

    public Optional<DetalleComprobante> findById(Long id) {
        return detalleComprobanteRepository.findById(id);
    }

    public DetalleComprobante save(DetalleComprobante detalle) {
        return detalleComprobanteRepository.save(detalle);
    }

    public void delete(Long id) {
        detalleComprobanteRepository.deleteById(id);
    }

    public List<DetalleComprobante> findByComprobanteId(Long comprobanteId) {
        try {
            return detalleComprobanteRepository.findByComprobanteId(comprobanteId);
        } catch (NoSuchMethodError | UnsupportedOperationException e) {
            return detalleComprobanteRepository.findAll().stream().filter(d -> 
            d.getComprobante() != null && d.getComprobante()
            .getId().equals(comprobanteId)).toList();
        }
    }

}
