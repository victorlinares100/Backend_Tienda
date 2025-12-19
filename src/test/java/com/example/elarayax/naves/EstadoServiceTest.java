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

import com.example.elarayax.naves.model.Estado;
import com.example.elarayax.naves.repository.EstadoRepository;
import com.example.elarayax.naves.service.EstadoService;

class EstadoServiceTest {

    @Mock
    private EstadoRepository estadoRepository;

    @InjectMocks
    private EstadoService estadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void partialUpdate_existing_updatesNombre() {
        // Datos de prueba
        Estado existing = new Estado();
        existing.setId(1L);
        existing.setNombreEstado("Pendiente");

        Estado update = new Estado();
        update.setId(1L);
        update.setNombreEstado("Completado");

        // Simulación (Mocking)
        when(estadoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(estadoRepository.save(any(Estado.class))).thenReturn(existing);

        // Ejecución
        Estado result = estadoService.partialUpdate(update);

        // Verificaciones
        assertNotNull(result);
        assertEquals("Completado", result.getNombreEstado());
        verify(estadoRepository).findById(1L);
        verify(estadoRepository).save(existing);
    }

    @Test
    void partialUpdate_nonExisting_returnsNull() {
        // Datos de prueba
        Estado update = new Estado();
        update.setId(99L);

        // Simulación (Mocking)
        when(estadoRepository.findById(99L)).thenReturn(Optional.empty());

        // Ejecución
        Estado result = estadoService.partialUpdate(update);

        // Verificaciones
        assertNull(result);
        verify(estadoRepository).findById(99L);
        verify(estadoRepository, never()).save(any());
    }
}