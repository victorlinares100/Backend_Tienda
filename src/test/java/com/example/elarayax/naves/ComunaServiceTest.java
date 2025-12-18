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

import com.example.elarayax.naves.model.Comuna;
import com.example.elarayax.naves.repository.ComunaRepository;

class ComunaServiceTest {
    @Mock
    private ComunaRepository comunaRepository;

    @InjectMocks
    private ComunaService comunaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

        @Test
    void findById_existing_returnsComuna() {
        Comuna comuna = new Comuna();
        comuna.setId(1L);

        when(comunaRepository.findById(1L))
                .thenReturn(Optional.of(comuna));

        Comuna result = comunaService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

        @Test
    void findById_nonExisting_returnsNull() {
        when(comunaRepository.findById(99L))
                .thenReturn(Optional.empty());

        Comuna result = comunaService.findById(99L);

        assertNull(result);
    }

        @Test
    void partialUpdate_existing_updatesFields() {
        Comuna existing = new Comuna();
        existing.setId(1L);
        existing.setNombre("Viejo Nombre");

        Comuna update = new Comuna();
        update.setId(1L);
        update.setNombre("Nuevo Nombre");

        when(comunaRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(comunaRepository.save(existing))
                .thenReturn(existing);

        Comuna result = comunaService.partialUpdate(update);

        assertNotNull(result);
        assertEquals("Nuevo Nombre", result.getNombre());
    }

        @Test
    void partialUpdate_nonExisting_returnsNull() {
        Comuna update = new Comuna();
        update.setId(99L);

        when(comunaRepository.findById(99L))
                .thenReturn(Optional.empty());

        Comuna result = comunaService.partialUpdate(update);

        assertNull(result);
        verify(comunaRepository, never()).save(any());
    }
}
