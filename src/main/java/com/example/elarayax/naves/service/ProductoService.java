package com.example.elarayax.naves.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.elarayax.naves.model.Producto;
import com.example.elarayax.naves.repository.ProductoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto update(Long id, Producto updated) {
        Producto producto = findById(id);

        if (producto == null) return null;

        producto.setNombreProducto(updated.getNombreProducto());
        producto.setPrecioProducto(updated.getPrecioProducto());
        producto.setStock(updated.getStock());
        producto.setCategoria(updated.getCategoria());
        producto.setMarca(updated.getMarca());
        producto.setTalla(updated.getTalla());
        producto.setImagenUrl(updated.getImagenUrl());

        return productoRepository.save(producto);
    }

    public void delete(Long id) {
        productoRepository.deleteById(id);
    }
}
