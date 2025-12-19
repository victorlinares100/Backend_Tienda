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
        // 1. Preparar datos de prueba
        Marca existing = new Marca();
        existing.setId(1L);
        existing.setNombreMarca("Nike");

        Marca update = new Marca();
        update.setId(1L);
        update.setNombreMarca("Adidas");

        // 2. Mockear el comportamiento del repositorio
        when(marcaRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        // Usamos any() en el save para evitar problemas de referencia de objetos
        when(marcaRepository.save(any(Marca.class)))
                .thenReturn(existing);

        // 3. Ejecutar el método del servicio
        Marca result = marcaService.partialUpdate(update);

        // 4. Verificaciones
        assertNotNull(result);
        assertEquals("Adidas", result.getNombreMarca());
        verify(marcaRepository).findById(1L);
        verify(marcaRepository).save(existing);
    }

    @Test
    void partialUpdate_nonExisting_returnsNull() {
        // 1. Datos para un ID que no existe
        Marca update = new Marca();
        update.setId(99L);

        // 2. Mockear retorno vacío
        when(marcaRepository.findById(99L))
                .thenReturn(Optional.empty());

        // 3. Ejecutar
        Marca result = marcaService.partialUpdate(update);

        // 4. Verificar que sea nulo y que NUNCA se llame al save
        assertNull(result);
        verify(marcaRepository).findById(99L);
        verify(marcaRepository, never()).save(any());
    }
}