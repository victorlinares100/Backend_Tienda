package com.example.elarayax.naves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.elarayax.naves.model.Comprobante;
import com.example.elarayax.naves.repository.ComprobanteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComprobanteService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    public List<Comprobante> findAll() {
        return comprobanteRepository.findAll();
    }

    public Comprobante findById(Long id) {
        return comprobanteRepository.findById(id).orElse(null);
    }

    public Comprobante save(Comprobante comprobante) {
        return comprobanteRepository.save(comprobante);
    }

    public void delete(Long id) {
        comprobanteRepository.deleteById(id);
    }
    public Comprobante partialUpdate(Comprobante comprobante){
        Comprobante existingComprobante = comprobanteRepository.findById(comprobante.getId()).orElse(null);
        if (existingComprobante != null) {
            if (comprobante.getFechaOrden() != null) {
                existingComprobante.setFechaOrden(comprobante.getFechaOrden());
            }
                if (comprobante.getTotalCompra() != null) {
                existingComprobante.setTotalCompra(comprobante.getTotalCompra());
            }
            if (comprobante.getUsuario() != null) {
                existingComprobante.setUsuario(comprobante.getUsuario());
            }
        
            if(comprobante.getMetodoPago() != null) {
                existingComprobante.setMetodoPago(comprobante.getMetodoPago());
            }

            if(comprobante.getEstado() != null) {
                existingComprobante.setEstado(comprobante.getEstado());
            }

            if(comprobante.getEnvio() != null) {
                existingComprobante.setEnvio(comprobante.getEnvio());
            }

            return comprobanteRepository.save(existingComprobante);
        }
        return null;
    }
}
