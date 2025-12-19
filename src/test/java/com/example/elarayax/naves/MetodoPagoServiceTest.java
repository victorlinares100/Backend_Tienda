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

import com.example.elarayax.naves.model.MetodoPago;
import com.example.elarayax.naves.repository.MetodoPagoRepository;
import com.example.elarayax.naves.service.MetodoPagoService;

class MetodoPagoServiceTest {

    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    @InjectMocks
    private MetodoPagoService metodoPagoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void partialUpdate_existing_updatesFields() {

        MetodoPago existing = new MetodoPago();
        existing.setId(1L);
        existing.setNombreMetodo("Tarjeta");
        existing.setDescripcion("Crédito");
        existing.setComision(2.5);

        MetodoPago update = new MetodoPago();
        update.setId(1L);
        update.setNombreMetodo("Débito");
        update.setDescripcion("Cuenta corriente");
        update.setComision(1.0);

        when(metodoPagoRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(metodoPagoRepository.save(existing))
                .thenReturn(existing);

        MetodoPago result = metodoPagoService.partialUpdate(update);

        assertNotNull(result);
        assertEquals("Débito", result.getNombreMetodo());
        assertEquals("Cuenta corriente", result.getDescripcion());
        assertEquals(1.0, result.getComision());
    }

    @Test
    void partialUpdate_nonExisting_returnsNull() {

        MetodoPago update = new MetodoPago();
        update.setId(99L);

        when(metodoPagoRepository.findById(99L))
                .thenReturn(Optional.empty());

        MetodoPago result = metodoPagoService.partialUpdate(update);

        assertNull(result);
        verify(metodoPagoRepository, never()).save(any());
    }
}
