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

import com.example.elarayax.naves.model.Talla;
import com.example.elarayax.naves.repository.TallaRepository;

class TallaServiceTest {

    @Mock
    private TallaRepository tallaRepository;

    @InjectMocks
    private TallaService tallaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void partialUpdate_existing_updatesTipoTalla() {

        Talla existing = new Talla();
        existing.setId(1L);
        existing.setTipoTalla("M");

        Talla update = new Talla();
        update.setId(1L);
        update.setTipoTalla("L");

        when(tallaRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(tallaRepository.save(existing))
                .thenReturn(existing);

        Talla result = tallaService.partialUpdate(update);

        assertNotNull(result);
        assertEquals("L", result.getTipoTalla());
    }

    @Test
    void partialUpdate_nonExisting_returnsNull() {

        Talla update = new Talla();
        update.setId(99L);

        when(tallaRepository.findById(99L))
                .thenReturn(Optional.empty());

        Talla result = tallaService.partialUpdate(update);

        assertNull(result);
        verify(tallaRepository, never()).save(any());
    }
}
