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
import com.example.elarayax.naves.dto.ProductoDTO;

import com.example.elarayax.naves.model.Producto;
import com.example.elarayax.naves.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.findAll();

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);

        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(producto);
    }

    // Crear producto con DTO
    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody ProductoDTO dto) {

        Producto created = productoService.createFromDTO(dto);

        return ResponseEntity.status(201).body(created);
    }

    // Actualizar producto completo con DTO
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(
            @PathVariable Long id,
            @RequestBody ProductoDTO dto) {

        Producto actualizado = productoService.updateFromDTO(id, dto);

        return ResponseEntity.ok(actualizado);
    }

    // Patch parcial con DTO
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> patchProducto(
            @PathVariable Long id,
            @RequestBody ProductoDTO dto) {

        Producto actualizado = productoService.updateFromDTO(id, dto);

        return ResponseEntity.ok(actualizado);
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


