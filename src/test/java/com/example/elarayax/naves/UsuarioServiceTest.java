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

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.elarayax.naves.model.Usuario;
import com.example.elarayax.naves.repository.UsuarioRepository;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_correctCredentials_returnsUsuario() {

        Usuario input = new Usuario();
        input.setCorreo("test@mail.com");
        input.setContrasena("1234");

        Usuario stored = new Usuario();
        stored.setCorreo("test@mail.com");
        stored.setContrasena("hashed");

        when(usuarioRepository.findByCorreo("test@mail.com"))
                .thenReturn(stored);
        when(passwordEncoder.matches("1234", "hashed"))
                .thenReturn(true);

        Usuario result = usuarioService.login(input);

        assertNotNull(result);
        assertEquals("test@mail.com", result.getCorreo());
    }

    @Test
    void login_wrongPassword_returnsNull() {

        Usuario input = new Usuario();
        input.setCorreo("test@mail.com");
        input.setContrasena("wrong");

        Usuario stored = new Usuario();
        stored.setCorreo("test@mail.com");
        stored.setContrasena("hashed");

        when(usuarioRepository.findByCorreo("test@mail.com"))
                .thenReturn(stored);
        when(passwordEncoder.matches("wrong", "hashed"))
                .thenReturn(false);

        Usuario result = usuarioService.login(input);

        assertNull(result);
    }

    @Test
    void save_encodesPassword_beforeSaving() {

        Usuario usuario = new Usuario();
        usuario.setContrasena("plain");

        when(passwordEncoder.encode("plain"))
                .thenReturn("encoded");
        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        Usuario result = usuarioService.save(usuario);

        assertEquals("encoded", result.getContrasena());
        verify(passwordEncoder).encode("plain");
    }

    @Test
    void partialUpdate_existing_updatesPassword() {

        Usuario existing = new Usuario();
        existing.setId(1L);
        existing.setContrasena("old");

        Usuario update = new Usuario();
        update.setId(1L);
        update.setContrasena("new");

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("new"))
                .thenReturn("encodedNew");
        when(usuarioRepository.save(existing))
                .thenReturn(existing);

        Usuario result = usuarioService.partialUpdate(update);

        assertNotNull(result);
        assertEquals("encodedNew", result.getContrasena());
    }

    @Test
    void partialUpdate_nonExisting_returnsNull() {

        Usuario update = new Usuario();
        update.setId(99L);

        when(usuarioRepository.findById(99L))
                .thenReturn(Optional.empty());

        Usuario result = usuarioService.partialUpdate(update);

        assertNull(result);
        verify(usuarioRepository, never()).save(any());
    }
}
