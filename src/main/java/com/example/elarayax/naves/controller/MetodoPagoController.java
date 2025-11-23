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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elarayax.naves.model.MetodoPago;
import com.example.elarayax.naves.service.MetodoPagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<List<MetodoPago>> getAll() {
        List<MetodoPago> list = metodoPagoService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> getById(@PathVariable Long id) {
        MetodoPago mp = metodoPagoService.findById(id);
        if (mp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mp);
    }

    @PostMapping
    public ResponseEntity<MetodoPago> create(@Valid @RequestBody MetodoPago metodoPago) {
        MetodoPago saved = metodoPagoService.save(metodoPago);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> update(@PathVariable Long id, @Valid @RequestBody MetodoPago metodoPago) {
        MetodoPago existing = metodoPagoService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        metodoPago.setId(existing.getId());
        MetodoPago updated = metodoPagoService.save(metodoPago);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MetodoPago> updateParcialMetodoPago(@PathVariable Long id, @RequestBody MetodoPago metodoPago) {
        metodoPago.setId(id);
        MetodoPago updatedMetodoPago = metodoPagoService.partialUpdate(metodoPago);
        if (updatedMetodoPago == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMetodoPago);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        MetodoPago existing = metodoPagoService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        metodoPagoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
