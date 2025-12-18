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
import static org.mockito.Mockito.when;

import com.example.elarayax.naves.model.Rol;
import com.example.elarayax.naves.repository.RolRepository;

class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void partialUpdate_existing_updatesNombre() {

        Rol existing = new Rol();
        existing.setId(1L);
        existing.setNombre("USER");

        Rol update = new Rol();
        update.setId(1L);
        update.setNombre("ADMIN");

        when(rolRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(rolRepository.save(existing))
                .thenReturn(existing);

        Rol result = rolService.partialUpdate(update);

        assertNotNull(result);
        assertEquals("ADMIN", result.getNombre());
    }

    @Test
    void partialUpdate_nonExisting_returnsNull() {

        Rol update = new Rol();
        update.setId(99L);

        when(rolRepository.findById(99L))
                .thenReturn(Optional.empty());

        Rol result = rolService.partialUpdate(update);

        assertNull(result);
        verify(rolRepository, never()).save(any());
    }
}
