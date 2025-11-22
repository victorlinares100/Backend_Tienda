package com.example.elarayax.naves.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.elarayax.naves.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Este método permite buscar por nombre
    Categoria findByNombreCategoria(String nombre);
}
