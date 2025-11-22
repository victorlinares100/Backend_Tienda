package com.example.elarayax.naves.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.example.elarayax.naves.service.ImagenService;
import com.example.elarayax.naves.model.Imagen;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    private final ImagenService imagenService;

    // Inyección por constructor
    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    // Crear
    @PostMapping
    public ResponseEntity<Imagen> createImagen(@RequestBody Imagen imagen) {
        Imagen saved = imagenService.save(imagen);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Obtener todas
    @GetMapping
    public List<Imagen> getAll() {
        return imagenService.getAll();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Imagen> getById(@PathVariable Long id) {
        Imagen imagen = imagenService.findById(id);
        if (imagen == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(imagen);
    }

    // Actualizar completa
    @PutMapping("/{id}")
    public ResponseEntity<Imagen> updateImagen(@PathVariable Long id, @RequestBody Imagen imagen) {
        Imagen updated = imagenService.update(id, imagen);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // Actualización parcial
    @PatchMapping("/{id}")
    public ResponseEntity<Imagen> patchImagen(@PathVariable Long id, @RequestBody Imagen imagen) {
        Imagen patched = imagenService.patch(id, imagen);
        if (patched == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(patched);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImagen(@PathVariable Long id) {
        boolean deleted = imagenService.delete(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
