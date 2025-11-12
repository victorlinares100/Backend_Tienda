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

import com.example.elarayax.naves.service.TallaService;
import com.example.elarayax.naves.model.Talla;

@RestController
@RequestMapping("/api/tallas")
public class TallaController {
    @Autowired
    private TallaService tallaService;

    @GetMapping
    public ResponseEntity<List<Talla>> getAllTalla() {
        List<Talla> tallas = tallaService.findAll();
        if (tallas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tallas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talla> getTallaById(@PathVariable Integer id) {
        Talla talla = tallaService.findById(id);
        if (talla == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(talla);
    }

    @PostMapping
    public ResponseEntity<Talla> createTalla(@RequestBody Talla talla) {
        Talla createdTalla = tallaService.save(talla);
        return ResponseEntity.status(201).body(createdTalla);
    }


}
