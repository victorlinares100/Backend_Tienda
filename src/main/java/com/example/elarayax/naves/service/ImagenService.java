package com.example.elarayax.naves.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.elarayax.naves.model.Imagen;
import com.example.elarayax.naves.repository.ImagenRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImagenService {

    private final ImagenRepository imagenRepository;

    public ImagenService(ImagenRepository imagenRepository) {
        this.imagenRepository = imagenRepository;
    }

    // Crear o guardar
    public Imagen save(Imagen imagen) {
        return imagenRepository.save(imagen);
    }

    // Obtener todas
    public List<Imagen> getAll() {
        return imagenRepository.findAll();
    }

    // Obtener por ID
    public Imagen findById(Long id) {
        Optional<Imagen> optional = imagenRepository.findById(id);
        return optional.orElse(null);
    }

    // Actualizar completa (solo URL)
    public Imagen update(Long id, Imagen imagen) {
        return imagenRepository.findById(id).map(existing -> {
            existing.setUrl(imagen.getUrl());
            return imagenRepository.save(existing);
        }).orElse(null);
    }

    // Actualización parcial (PATCH, solo URL)
    public Imagen patch(Long id, Imagen imagen) {
        return imagenRepository.findById(id).map(existing -> {
            if (imagen.getUrl() != null) existing.setUrl(imagen.getUrl());
            return imagenRepository.save(existing);
        }).orElse(null);
    }

    // Eliminar
    public boolean delete(Long id) {
        return imagenRepository.findById(id).map(existing -> {
            imagenRepository.delete(existing);
            return true;
        }).orElse(false);
    }
}
