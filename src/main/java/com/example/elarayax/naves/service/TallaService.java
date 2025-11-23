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
    public Talla findById(Long id) {
        return tallaRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Talla save(Talla talla) {
        return tallaRepository.save(talla);
    } 
    public void deleteById(Long id) {
        tallaRepository.deleteById(id);
    }

    public Talla partialUpdate(Talla talla){
        Talla existingTalla = tallaRepository.findById(talla.getId()).orElse(null);
        if (existingTalla != null) {
            if (talla.getTipoTalla() != null) {
                existingTalla.setTipoTalla(talla.getTipoTalla());
            }


            return tallaRepository.save(existingTalla);
        }
        return null;
    }

}
