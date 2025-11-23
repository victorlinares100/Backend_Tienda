package com.example.elarayax.naves.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.example.elarayax.naves.model.DetalleComprobante;
import com.example.elarayax.naves.service.DetalleComprobanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/detallecomprobantes")
public class DetalleComprobanteController {

    @Autowired
    private DetalleComprobanteService detalleComprobanteService;

    @GetMapping
    public ResponseEntity<List<DetalleComprobante>> getAll() {
        List<DetalleComprobante> list = detalleComprobanteService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleComprobante> getById(@PathVariable Long id) {
        Optional<DetalleComprobante> maybe = detalleComprobanteService.findById(id);
        return maybe.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetalleComprobante> create(@Valid @RequestBody DetalleComprobante detalleComprobante) {
        DetalleComprobante saved = detalleComprobanteService.save(detalleComprobante);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleComprobante> update(@PathVariable Long id, @Valid @RequestBody DetalleComprobante detalleComprobante) {
        return detalleComprobanteService.findById(id).map(existing -> {
            detalleComprobante.setId(existing.getId());
            DetalleComprobante updated = detalleComprobanteService.save(detalleComprobante);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DetalleComprobante> partialUpdate(@PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return detalleComprobanteService.findById(id).map(e -> {
            if (updates.containsKey("cantidad")) {
                Object c = updates.get("cantidad");
                if (c instanceof Number) {
                    e.setCantidad(((Number) c).intValue());
                }
            }
            if (updates.containsKey("precioUnitario")) {
                Object p = updates.get("precioUnitario");
                if (p instanceof Number) {
                    e.setPrecioUnitario(((Number) p).doubleValue());
                }
            }
            DetalleComprobante saved = detalleComprobanteService.save(e);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return detalleComprobanteService.findById(id).map(e -> {
            detalleComprobanteService.delete(id);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}