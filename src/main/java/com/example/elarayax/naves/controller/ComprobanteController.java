package com.example.elarayax.naves.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elarayax.naves.dto.CarritoRequest;
import com.example.elarayax.naves.model.Comprobante;
import com.example.elarayax.naves.service.ComprobanteService;

@RestController
@RequestMapping("/api/comprobantes")
public class ComprobanteController {

    @Autowired
    private ComprobanteService comprobanteService;

    @GetMapping
    public ResponseEntity<List<Comprobante>> getAllComprobantes() {
        List<Comprobante> comprobantes = comprobanteService.findAll();
        if (comprobantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comprobantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comprobante> getComprobanteById(@PathVariable Long id) {
        Comprobante comprobante = comprobanteService.findById(id);
        if (comprobante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comprobante);
    }

    @PostMapping("/carrito")
    public ResponseEntity<?> crearDesdeCarrito(@RequestBody CarritoRequest request) {
        try {
            Comprobante comprobante = comprobanteService.crearComprobanteDesdeCarrito(request);
            return ResponseEntity.status(201).body(comprobante);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comprobante> updateParcialComprobante(@PathVariable Long id, @RequestBody Comprobante comprobante) {
        comprobante.setId(id);
        Comprobante updatedComprobante = comprobanteService.partialUpdate(comprobante);
        if (updatedComprobante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedComprobante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComprobante(@PathVariable Long id) {
        comprobanteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
