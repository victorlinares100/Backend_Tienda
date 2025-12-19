package com.example.elarayax.naves.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.elarayax.naves.model.Usuario;
import com.example.elarayax.naves.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario login(Usuario usuario) {
        Usuario foundUsuario = usuarioRepository.findByCorreo(usuario.getCorreo());

        if (foundUsuario != null && passwordEncoder.matches(usuario.getContrasena(), foundUsuario.getContrasena())) {
            return foundUsuario;
        }

        return null;
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
            usuario.setRol("CLIENTE");
        }

        if (usuario.getContrasena() != null) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }

        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario partialUpdate(Usuario usuario) {
        Usuario existingUsuario = usuarioRepository.findById(usuario.getId()).orElse(null);
        if (existingUsuario != null) {

            if (usuario.getNombre() != null) {
                existingUsuario.setNombre(usuario.getNombre());
            }

            if (usuario.getCorreo() != null) {
                existingUsuario.setCorreo(usuario.getCorreo());
            }

            if (usuario.getContrasena() != null) {
                existingUsuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            }

            if (usuario.getRol() != null) {
                existingUsuario.setRol(usuario.getRol());
            }

            return usuarioRepository.save(existingUsuario);
        }
        return null;
    }
}
