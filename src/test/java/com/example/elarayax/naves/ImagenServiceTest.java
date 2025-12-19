package com.example.elarayax.naves;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import com.example.elarayax.naves.model.Imagen;
import com.example.elarayax.naves.repository.ImagenRepository;
import com.example.elarayax.naves.service.ImagenService;

class ImagenServiceTest {

    @Mock
    private ImagenRepository imagenRepository;

    @InjectMocks
    private ImagenService imagenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void update_existing_updatesUrl() {

        Imagen existing = new Imagen();
        existing.setId(1L);
        existing.setUrl("old.png");

        Imagen update = new Imagen();
        update.setUrl("new.png");

        when(imagenRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(imagenRepository.save(existing))
                .thenReturn(existing);

        Imagen result = imagenService.update(1L, update);

        assertNotNull(result);
        assertEquals("new.png", result.getUrl());
    }

    @Test
    void patch_nullUrl_keepsOriginal() {

        Imagen existing = new Imagen();
        existing.setId(1L);
        existing.setUrl("original.png");

        Imagen patch = new Imagen(); // url = null

        when(imagenRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(imagenRepository.save(existing))
                .thenReturn(existing);

        Imagen result = imagenService.patch(1L, patch);

        assertNotNull(result);
        assertEquals("original.png", result.getUrl());
    }
    
    @Test
    void delete_existing_returnsTrue() {

        Imagen existing = new Imagen();
        existing.setId(1L);

        when(imagenRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        boolean result = imagenService.delete(1L);

        assertTrue(result);
        verify(imagenRepository).delete(existing);
    }

    @Test
    void delete_nonExisting_returnsFalse() {

        when(imagenRepository.findById(99L))
                .thenReturn(Optional.empty());

        boolean result = imagenService.delete(99L);

        assertFalse(result);
        verify(imagenRepository, never()).delete(null);
    }
}
