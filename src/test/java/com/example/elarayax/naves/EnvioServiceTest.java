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

import com.example.elarayax.naves.model.Envio;
import com.example.elarayax.naves.repository.EnvioRepository;

class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
        @Test
    void partialUpdate_existing_updatesFields() {

        Envio existing = new Envio();
        existing.setId(1L);
        existing.setDireccionDestino("Calle Antigua");

        Envio update = new Envio();
        update.setId(1L);
        update.setDireccionDestino("Calle Nueva");

        when(envioRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(envioRepository.save(existing))
                .thenReturn(existing);

        Envio result = envioService.partialUpdate(update);

        assertNotNull(result);
        assertEquals("Calle Nueva", result.getDireccionDestino());
    }
        @Test
    void partialUpdate_nonExisting_returnsNull() {

        Envio update = new Envio();
        update.setId(99L);

        when(envioRepository.findById(99L))
                .thenReturn(Optional.empty());

        Envio result = envioService.partialUpdate(update);

        assertNull(result);
        verify(envioRepository, never()).save(any());
    }
        @Test
    void findById_returnsEnvio() {

        Envio envio = new Envio();
        envio.setId(1L);

        when(envioRepository.findById(1L))
                .thenReturn(Optional.of(envio));

        Optional<Envio> result = envioService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.get().getId());
    }

}
