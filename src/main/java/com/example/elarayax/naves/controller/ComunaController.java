package com.example.elarayax.naves.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.elarayax.naves.service.ComunaService;
import com.example.elarayax.naves.model.Comuna;

@RestController
@RequestMapping("/api/comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<Comuna>> getAllComuna() {
        List<Comuna> comuna = comunaService.findAll();
        if (comuna.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comuna);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comuna> getRegionById(@PathVariable Long id) {
        Comuna comuna = comunaService.findById(id);
        if (comuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comuna);
    }

    @PostMapping
    public ResponseEntity<Comuna> createRegion(@RequestBody Comuna comuna) {
        Comuna createdComuna = comunaService.save(comuna);
        return ResponseEntity.status(201).body(createdComuna);
    }
    
}
