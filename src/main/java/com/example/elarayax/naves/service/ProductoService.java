package com.example.elarayax.naves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.elarayax.naves.model.Categoria;
import com.example.elarayax.naves.model.Marca;
import com.example.elarayax.naves.model.Producto;
import com.example.elarayax.naves.model.Talla;
import com.example.elarayax.naves.model.Imagen;
import com.example.elarayax.naves.dto.ProductoDTO;
import com.example.elarayax.naves.repository.CategoriaRepository;
import com.example.elarayax.naves.repository.MarcaRepository;
import com.example.elarayax.naves.repository.ProductoRepository;
import com.example.elarayax.naves.repository.TallaRepository;
import com.example.elarayax.naves.repository.ImagenRepository;

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

    public Producto createFromDTO(ProductoDTO dto) {
        Producto p = new Producto();
        applyDTOtoProducto(p, dto);
        return productoRepository.save(p);
    }

    public Producto updateFromDTO(Long id, ProductoDTO dto) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        applyDTOtoProducto(p, dto);
        return productoRepository.save(p);
    }

    private void applyDTOtoProducto(Producto p, ProductoDTO dto) {
        if (dto.getNombreProducto() != null) p.setNombreProducto(dto.getNombreProducto());
        if (dto.getPrecioProducto() != null) p.setPrecioProducto(dto.getPrecioProducto());
        if (dto.getStock() != null) p.setStock(dto.getStock());

        if (dto.getCategoriaId() != null) {
            Categoria c = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            p.setCategoria(c);
        }

        if (dto.getMarcaId() != null) {
            Marca m = marcaRepository.findById(dto.getMarcaId())
                    .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
            p.setMarca(m);
        }

        if (dto.getTallaId() != null) {
            Talla t = tallaRepository.findById(dto.getTallaId())
                    .orElseThrow(() -> new RuntimeException("Talla no encontrada"));
            p.setTalla(t);
        }

        if (dto.getImagenUrl() != null && !dto.getImagenUrl().isEmpty()) {
            Imagen img = new Imagen();
            img.setUrl(dto.getImagenUrl()); 
            imagenRepository.save(img);
            p.setImagen(img);
        }
    }
     public Producto partialUpdate(Producto producto){
        Producto existingProducto = productoRepository.findById(producto.getId()).orElse(null);
        if (existingProducto != null) {
            if (producto.getNombreProducto() != null) {
                existingProducto.setNombreProducto(producto.getNombreProducto());
            }
                if (producto.getPrecioProducto() != null) {
                existingProducto.setPrecioProducto(producto.getPrecioProducto());
            }
            if (producto.getStock() != null) {
                existingProducto.setStock(producto.getStock());
            }
        
            if(producto.getMarca() != null) {
                existingProducto.setMarca(producto.getMarca());
            }

            if(producto.getTalla() != null) {
                existingProducto.setTalla(producto.getTalla());
            }

            if(producto.getImagen() != null) {
                existingProducto.setImagen(producto.getImagen());
            }

            return productoRepository.save(existingProducto);
        }
        return null;
    }
}

