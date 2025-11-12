package com.example.elarayax.naves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.elarayax.naves.model.Imagen;
import com.example.elarayax.naves.repository.ImagenRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImagenService {
    @Autowired
    private ImagenRepository imagenRepository;

    public List<Imagen> findAll() {
        return imagenRepository.findAll();
    }

    @SuppressWarnings("null")
    public Imagen findById(Integer id) {
        return imagenRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Imagen save(Imagen imagen) {
        return imagenRepository.save(imagen);
    }
    

}
