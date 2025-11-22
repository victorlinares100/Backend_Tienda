package com.example.elarayax.naves.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.elarayax.naves.model.Imagen;
import com.example.elarayax.naves.dto.ProductoDTO;
import com.example.elarayax.naves.model.Categoria;
import com.example.elarayax.naves.model.Marca;
import com.example.elarayax.naves.model.Producto;
import com.example.elarayax.naves.model.Talla;
import com.example.elarayax.naves.repository.CategoriaRepository;
import com.example.elarayax.naves.repository.ImagenRepository;
import com.example.elarayax.naves.repository.MarcaRepository;
import com.example.elarayax.naves.repository.ProductoRepository;
import com.example.elarayax.naves.repository.TallaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private TallaRepository tallaRepository;

    @Autowired
    private ImagenRepository imagenRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    // 🔹 Crear desde DTO
    public Producto createFromDTO(ProductoDTO dto) {
        Producto p = new Producto();
        applyDTOtoProducto(p, dto);
        return productoRepository.save(p);
    }

    // 🔹 Actualizar desde DTO
    public Producto updateFromDTO(Long id, ProductoDTO dto) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        applyDTOtoProducto(p, dto);
        return productoRepository.save(p);
    }

    // 🔹 Aplicar DTO a la entidad
    private void applyDTOtoProducto(Producto p, ProductoDTO dto) {
        if (dto.getNombreProducto() != null)
            p.setNombreProducto(dto.getNombreProducto());
        if (dto.getPrecioProducto() != null)
            p.setPrecioProducto(dto.getPrecioProducto());
        if (dto.getStock() != null)
            p.setStock(dto.getStock());

        if (dto.getCategoria() != null) {
            Categoria c = categoriaRepository.findByTipoCategoria(dto.getCategoria());
            p.setCategoria(c);
        }
        if (dto.getMarca() != null) {
            Marca m = marcaRepository.findByNombreMarca(dto.getMarca());
            p.setMarca(m);
        }
        if (dto.getTalla() != null) {
            Talla t = tallaRepository.findByTipoTalla(dto.getTalla());
            p.setTalla(t);
        }
        if (dto.getImagenUrl() != null) {
            Imagen img = new Imagen();
            img.setUrl(dto.getImagenUrl());
            imagenRepository.save(img);
            p.setImagen(img);
        }
    }
}

