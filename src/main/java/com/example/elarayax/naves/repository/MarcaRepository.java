package com.example.elarayax.naves.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.elarayax.naves.model.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Marca findByNombreMarca(String nombre);
}
