package com.example.elarayax.naves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.elarayax.naves.model.Talla;
import com.example.elarayax.naves.repository.TallaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TallaService {
    @Autowired
    private TallaRepository tallaRepository;

    public List<Talla> findAll() {
        return tallaRepository.findAll();
    }

    @SuppressWarnings("null")
    public Talla findById(Integer id) {
        return tallaRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Talla save(Talla talla) {
        return tallaRepository.save(talla);
    } 

}
