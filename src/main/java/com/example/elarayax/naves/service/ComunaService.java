package com.example.elarayax.naves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.elarayax.naves.model.Comuna;
import com.example.elarayax.naves.repository.ComunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> findAll() {
        return comunaRepository.findAll();
    }

    @SuppressWarnings("null")
    public Comuna findById(Long id) {
        return comunaRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Comuna save(Comuna comuna) {
        return comunaRepository.save(comuna);
    }
    
     public void deleteById(Long id) {
        comunaRepository.deleteById(id);
    }

    public Comuna partialUpdate(Comuna comuna){
        Comuna existingComuna = comunaRepository.findById(comuna.getId()).orElse(null);
        if (existingComuna != null) {
            if (comuna.getNombre() != null) {
                existingComuna.setNombre(comuna.getNombre());
            }
                if (comuna.getRegion() != null) {
                existingComuna.setRegion(comuna.getRegion());
            }
            return comunaRepository.save(existingComuna);
        }
        return null;
    }
}

