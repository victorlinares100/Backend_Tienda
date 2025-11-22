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
import com.example.elarayax.naves.model.Producto;
import com.example.elarayax.naves.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
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

    // Crear producto
    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {

        // Validar campos obligatorios
        if (producto.getNombreProducto() == null ||
            producto.getPrecioProducto() == null ||
            producto.getStock() == null ||
            producto.getCategoria() == null ||
            producto.getMarca() == null ||
            producto.getTalla() == null ||
            producto.getImagenUrl() == null) {

            return ResponseEntity.badRequest().build();
        }

        Producto createdProducto = productoService.save(producto);
        return ResponseEntity.status(201).body(createdProducto);
    }

    // Actualizar producto completo
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(
            @PathVariable Long id,
            @RequestBody Producto productoActualizado) {

        Producto producto = productoService.findById(id);

        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        producto.setNombreProducto(productoActualizado.getNombreProducto());
        producto.setPrecioProducto(productoActualizado.getPrecioProducto());
        producto.setStock(productoActualizado.getStock());
        producto.setCategoria(productoActualizado.getCategoria());
        producto.setMarca(productoActualizado.getMarca());
        producto.setTalla(productoActualizado.getTalla());
        producto.setImagenUrl(productoActualizado.getImagenUrl());

        Producto actualizado = productoService.save(producto);
        return ResponseEntity.ok(actualizado);
    }

    // Actualizar solo algunos campos
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> patchProducto(
            @PathVariable Long id,
            @RequestBody Producto cambios) {

        Producto producto = productoService.findById(id);

        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        if (cambios.getNombreProducto() != null) producto.setNombreProducto(cambios.getNombreProducto());
        if (cambios.getPrecioProducto() != null) producto.setPrecioProducto(cambios.getPrecioProducto());
        if (cambios.getStock() != null) producto.setStock(cambios.getStock());
        if (cambios.getCategoria() != null) producto.setCategoria(cambios.getCategoria());
        if (cambios.getMarca() != null) producto.setMarca(cambios.getMarca());
        if (cambios.getTalla() != null) producto.setTalla(cambios.getTalla());
        if (cambios.getImagenUrl() != null) producto.setImagenUrl(cambios.getImagenUrl());

        Producto actualizado = productoService.save(producto);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
