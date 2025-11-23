package com.example.elarayax.naves.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.elarayax.naves.service.EstadoService;
import com.example.elarayax.naves.model.Estado;

@RestController
@RequestMapping("/api/estados")


public class EstadoController {
    @Autowired
    private EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<Estado>> getAllEstado() {
        List<Estado> estados = estadoService.findAll();
        if (estados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> getEstadoById(@PathVariable Long id) {
        Estado estado = estadoService.findById(id);
        if (estado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estado);
    }

    @PostMapping
    public ResponseEntity<Estado> createEstado(@RequestBody Estado estado) {
        Estado createdEstado = estadoService.save(estado);
        return ResponseEntity.status(201).body(createdEstado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> updateEstado(@PathVariable Long id, @RequestBody Estado estado) {
        estado.setId(id);
        Estado updatedEstado = estadoService.save(estado);
        if (updatedEstado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEstado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Estado> updateParcialEstado(@PathVariable Long id, @RequestBody Estado estado) {
        estado.setId(id);
        Estado updatedEstado = estadoService.partialUpdate(estado);
        if (updatedEstado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEstado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstado(@PathVariable Long id) {
        estadoService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
