package com.example.elarayax.naves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.elarayax.naves.model.Marca;
import com.example.elarayax.naves.repository.MarcaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }

    @SuppressWarnings("null")
    public Marca findById(Long id) {
        return marcaRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Marca save(Marca marca) {
        return marcaRepository.save(marca);
    } 

    public void delete(Long id) {
        marcaRepository.deleteById(id);
    }
     public void deleteById(Long id) {
        marcaRepository.deleteById(id);
    }

    public Marca partialUpdate(Marca marca){
        Marca existingMarca = marcaRepository.findById(marca.getId()).orElse(null);
        if (existingMarca != null) {
            if (marca.getNombreMarca() != null) {
                existingMarca.setNombreMarca(marca.getNombreMarca());
            }
            return marcaRepository.save(existingMarca);
        }
        return null;
    }
}



