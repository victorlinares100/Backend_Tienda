package com.example.elarayax.naves;

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

import com.example.elarayax.naves.model.Marca;
import com.example.elarayax.naves.repository.MarcaRepository;
import com.example.elarayax.naves.service.MarcaService;

class MarcaServiceTest {

    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void partialUpdate_existing_updatesNombreMarca() {

        Marca existing = new Marca();
        existing.setId(1L);
        existing.setNombreMarca("Nike");

        Marca update = new Marca();
        update.setId(1L);
        update.setNombreMarca("Adidas");

        when(marcaRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(marcaRepository.save(existing))
                .thenReturn(existing);

        Marca result = marcaService.partialUpdate(update);

        assertNotNull(result);
        assertEquals("Adidas", result.getNombreMarca());
    }

    @Test
    void partialUpdate_nonExisting_returnsNull() {

        Marca update = new Marca();
        update.setId(99L);

        when(marcaRepository.findById(99L))
                .thenReturn(Optional.empty());

        Marca result = marcaService.partialUpdate(update);

        assertNull(result);
        verify(marcaRepository, never()).save(any());
    }
}
