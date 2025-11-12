package com.example.elarayax.naves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.elarayax.naves.model.Categoria;
import com.example.elarayax.naves.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @SuppressWarnings("null")
    public Categoria findById(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    } 
}
