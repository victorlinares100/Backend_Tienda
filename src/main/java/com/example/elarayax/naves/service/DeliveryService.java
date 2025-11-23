package com.example.elarayax.naves.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.elarayax.naves.model.Delivery;
import com.example.elarayax.naves.repository.DeliveryRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    public Delivery findById(Long id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public void delete(Long id) {
        deliveryRepository.deleteById(id);
    }

    public void deleteById(Long id) {
        deliveryRepository.deleteById(id);
    }

    public Delivery partialUpdate(Delivery delivery){
        Delivery existingDelivery = deliveryRepository.findById(delivery.getId()).orElse(null);
        if (existingDelivery != null) {
            if (delivery.getFechaEntregaEstimada() != null) {
                existingDelivery.setFechaEntregaEstimada(delivery.getFechaEntregaEstimada());
            }
                if (delivery.getEstado() != null) {
                existingDelivery.setEstado(delivery.getEstado());
            }
            if (delivery.getEnvio() != null) {
                existingDelivery.setEnvio(delivery.getEnvio());
            }
             return deliveryRepository.save(existingDelivery);
        }
        return null;
    }
}
