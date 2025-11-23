package com.example.elarayax.naves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.elarayax.naves.model.Rol;
import com.example.elarayax.naves.repository.RolRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @SuppressWarnings("null")
    public Rol findById(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    } 
     public void deleteById(Long id) {
        rolRepository.deleteById(id);
    }

    public Rol partialUpdate(Rol rol){
        Rol existingRol = rolRepository.findById(rol.getId()).orElse(null);
        if (existingRol != null) {
            if (rol.getNombre() != null) {
                existingRol.setNombre(rol.getNombre());
            }


            return rolRepository.save(existingRol);
        }
        return null;
    }
}
