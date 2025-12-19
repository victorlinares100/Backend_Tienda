package com.example.elarayax.naves;

import java.util.Optional;
import java.time.LocalDate; // Importante para manejar fechas

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

import com.example.elarayax.naves.model.Delivery;
import com.example.elarayax.naves.repository.DeliveryRepository;
import com.example.elarayax.naves.service.DeliveryService;

class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_existing_returnsDelivery() {
        Delivery delivery = new Delivery();
        delivery.setId(1L);

        when(deliveryRepository.findById(1L))
                .thenReturn(Optional.of(delivery));

        Delivery result = deliveryService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_nonExisting_returnsNull() {
        when(deliveryRepository.findById(99L))
                .thenReturn(Optional.empty());

        Delivery result = deliveryService.findById(99L);

        assertNull(result);
    }

    @Test
    void partialUpdate_existing_updatesFields() {
        // 1. Datos iniciales
        Delivery existing = new Delivery();
        existing.setId(1L);
        // Supongamos que la fecha original era otra
        existing.setFechaEntregaEstimada(LocalDate.of(2024, 12, 31));

        // 2. Datos para la actualización
        LocalDate nuevaFecha = LocalDate.of(2025, 1, 1);
        Delivery update = new Delivery();
        update.setId(1L);
        update.setFechaEntregaEstimada(nuevaFecha);

        // 3. Comportamiento del Mock
        when(deliveryRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(deliveryRepository.save(any(Delivery.class)))
                .thenReturn(existing);

        // 4. Ejecución
        Delivery result = deliveryService.partialUpdate(update);

        // 5. Verificación
        assertNotNull(result);
        // Comparamos el objeto LocalDate, no un String
        assertEquals(nuevaFecha, result.getFechaEntregaEstimada());
    }

    @Test
    void partialUpdate_nonExisting_returnsNull() {
        Delivery update = new Delivery();
        update.setId(99L);

        when(deliveryRepository.findById(99L))
                .thenReturn(Optional.empty());

        Delivery result = deliveryService.partialUpdate(update);

        assertNull(result);
        verify(deliveryRepository, never()).save(any());
    }
}