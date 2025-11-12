package com.example.elarayax.naves.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.elarayax.naves.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {

}
