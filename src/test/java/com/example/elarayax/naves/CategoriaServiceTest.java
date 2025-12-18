package com.example.elarayax.naves;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.elarayax.naves.model.Categoria;
import com.example.elarayax.naves.repository.CategoriaRepository;
import com.example.elarayax.naves.service.CategoriaService;

class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_returnsAllCategorias() {
        Categoria c1 = new Categoria();
        Categoria c2 = new Categoria();

        when(categoriaRepository.findAll()).thenReturn(List.of(c1, c2));

        List<Categoria> result = categoriaService.findAll();

        assertEquals(2, result.size());
        verify(categoriaRepository).findAll();
    }

    @Test
    void findById_existingId_returnsCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoria));

        Categoria result = categoriaService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_nonExistingId_returnsNull() {
        when(categoriaRepository.findById(99L))
                .thenReturn(Optional.empty());

        Categoria result = categoriaService.findById(99L);

        assertNull(result);
    }

    @Test
    void save_returnsSavedCategoria() {
        Categoria categoria = new Categoria();
        categoria.setTipoCategoria("Exploración");

        when(categoriaRepository.save(categoria))
                .thenReturn(categoria);

        Categoria result = categoriaService.save(categoria);

        assertNotNull(result);
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void deleteById_callsRepository() {
        categoriaService.deleteById(1L);

        verify(categoriaRepository).deleteById(1L);
    }

    @Test
    void partialUpdate_existingCategoria_updatesTipoCategoria() {
        Categoria existing = new Categoria();
        existing.setId(1L);
        existing.setTipoCategoria("Vieja");

        Categoria update = new Categoria();
        update.setId(1L);
        update.setTipoCategoria("Nueva");

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(categoriaRepository.save(existing))
                .thenReturn(existing);

        Categoria result = categoriaService.partialUpdate(update);

        assertNotNull(result);
        assertEquals("Nueva", result.getTipoCategoria());
    }

    @Test
    void partialUpdate_nonExistingCategoria_returnsNull() {
        Categoria update = new Categoria();
        update.setId(99L);

        when(categoriaRepository.findById(99L))
                .thenReturn(Optional.empty());

        Categoria result = categoriaService.partialUpdate(update);

        assertNull(result);
        verify(categoriaRepository, never()).save(any());
    }
}
