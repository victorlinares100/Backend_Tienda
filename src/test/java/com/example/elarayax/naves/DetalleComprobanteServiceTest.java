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

import com.example.elarayax.naves.model.Comprobante;
import com.example.elarayax.naves.model.DetalleComprobante;

import com.example.elarayax.naves.repository.DetalleComprobanteRepository;
import com.example.elarayax.naves.service.DetalleComprobanteService;

class DetalleComprobanteServiceTest {

    @Mock
    private DetalleComprobanteRepository detalleComprobanteRepository;

    @InjectMocks
    private DetalleComprobanteService detalleComprobanteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

        @Test
    void partialUpdate_existing_updatesFields() {
        DetalleComprobante existing = new DetalleComprobante();
        existing.setId(1L);
        existing.setCantidad(1);

        DetalleComprobante update = new DetalleComprobante();
        update.setId(1L);
        update.setCantidad(5);

        when(detalleComprobanteRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(detalleComprobanteRepository.save(existing))
                .thenReturn(existing);

        DetalleComprobante result = detalleComprobanteService.partialUpdate(update);

        assertNotNull(result);
        assertEquals(5, result.getCantidad());
    }

        @Test
    void partialUpdate_nonExisting_returnsNull() {
        DetalleComprobante update = new DetalleComprobante();
        update.setId(99L);

        when(detalleComprobanteRepository.findById(99L))
                .thenReturn(Optional.empty());

        DetalleComprobante result = detalleComprobanteService.partialUpdate(update);

        assertNull(result);
        verify(detalleComprobanteRepository, never()).save(any());
    }

        @Test
    void findByComprobanteId_usesRepositoryMethod() {

        DetalleComprobante d1 = new DetalleComprobante();
        DetalleComprobante d2 = new DetalleComprobante();

        when(detalleComprobanteRepository.findByComprobanteId(1L))
                .thenReturn(List.of(d1, d2));

        List<DetalleComprobante> result =
                detalleComprobanteService.findByComprobanteId(1L);

        assertEquals(2, result.size());
    }

        @Test
    void findByComprobanteId_fallbacksToFiltering() {

        when(detalleComprobanteRepository.findByComprobanteId(1L))
                .thenThrow(new UnsupportedOperationException());

        Comprobante comprobante = new Comprobante();
        comprobante.setId(1L);

        DetalleComprobante match = new DetalleComprobante();
        match.setComprobante(comprobante);

        DetalleComprobante other = new DetalleComprobante();
        Comprobante otherComp = new Comprobante();
        otherComp.setId(2L);
        other.setComprobante(otherComp);

        when(detalleComprobanteRepository.findAll())
                .thenReturn(List.of(match, other));

        List<DetalleComprobante> result =
                detalleComprobanteService.findByComprobanteId(1L);

        assertEquals(1, result.size());
    }
}
