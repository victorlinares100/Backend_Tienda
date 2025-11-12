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
    public Marca findById(Integer id) {
        return marcaRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Marca save(Marca marca) {
        return marcaRepository.save(marca);
    } 

}
