package com.example.elarayax.naves;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.example.elarayax.naves.dto.ProductoDTO;
import com.example.elarayax.naves.model.Categoria;
import com.example.elarayax.naves.model.Marca;
import com.example.elarayax.naves.model.Producto;
import com.example.elarayax.naves.model.Talla;
import com.example.elarayax.naves.model.Imagen;

import com.example.elarayax.naves.repository.CategoriaRepository;
import com.example.elarayax.naves.repository.MarcaRepository;
import com.example.elarayax.naves.repository.ProductoRepository;
import com.example.elarayax.naves.repository.TallaRepository;
import com.example.elarayax.naves.repository.ImagenRepository;

import com.example.elarayax.naves.service.ProductoService;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private TallaRepository tallaRepository;

    @Mock
    private ImagenRepository imagenRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void createFromDTO_createsProductoCorrectly() {

        ProductoDTO dto = new ProductoDTO();
        dto.setNombreProducto("Polera");
        dto.setPrecioProducto(15000.0);
        dto.setStock(10);
        dto.setCategoriaId(1L);
        dto.setMarcaId(2L);
        dto.setTallaId(3L);
        dto.setImagenUrl("img.png");

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        Marca marca = new Marca();
        marca.setId(2L);

        Talla talla = new Talla();
        talla.setId(3L);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(marcaRepository.findById(2L)).thenReturn(Optional.of(marca));
        when(tallaRepository.findById(3L)).thenReturn(Optional.of(talla));
        when(productoRepository.save(org.mockito.ArgumentMatchers.any(Producto.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Producto result = productoService.createFromDTO(dto);

        assertNotNull(result);
        assertEquals("Polera", result.getNombreProducto());
        assertEquals(15000.0, result.getPrecioProducto());
        assertEquals(10, result.getStock());
        assertEquals(categoria, result.getCategoria());
        assertEquals(marca, result.getMarca());
        assertEquals(talla, result.getTalla());

        verify(imagenRepository).save(org.mockito.ArgumentMatchers.any(Imagen.class));
    }

    @Test
    void updateFromDTO_nonExisting_throwsException() {

        when(productoRepository.findById(99L))
                .thenReturn(Optional.empty());

        ProductoDTO dto = new ProductoDTO();

        assertThrows(RuntimeException.class, () ->
                productoService.updateFromDTO(99L, dto));
    }
    @Test
    void partialUpdate_updatesOnlyNonNullFields() {

        Producto existing = new Producto();
        existing.setId(1L);
        existing.setNombreProducto("Antiguo");
        existing.setStock(5);

        Producto update = new Producto();
        update.setId(1L);
        update.setStock(20);

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(productoRepository.save(existing))
                .thenReturn(existing);

        Producto result = productoService.partialUpdate(update);

        assertNotNull(result);
        assertEquals("Antiguo", result.getNombreProducto());
        assertEquals(20, result.getStock());
    }

}
