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

import com.example.elarayax.naves.model.Region;
import com.example.elarayax.naves.repository.RegionRepository;

class RegionServiceTest {
    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void partialUpdate_existing_updatesNombre() {

        Region existing = new Region();
        existing.setId(1L);
        existing.setNombre("Antigua");

        Region update = new Region();
        update.setId(1L);
        update.setNombre("Nueva");

        when(regionRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(regionRepository.save(existing))
                .thenReturn(existing);

        Region result = regionService.partialUpdate(update);

        assertNotNull(result);
        assertEquals("Nueva", result.getNombre());
    }

    @Test
    void partialUpdate_nonExisting_returnsNull() {

        Region update = new Region();
        update.setId(99L);

        when(regionRepository.findById(99L))
                .thenReturn(Optional.empty());

        Region result = regionService.partialUpdate(update);

        assertNull(result);
        verify(regionRepository, never()).save(any());
    }

}
