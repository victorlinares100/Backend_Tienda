package com.example.elarayax.naves.security;

import com.example.elarayax.naves.model.Usuario;
import com.example.elarayax.naves.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(email);
        if (usuario == null)
            throw new UsernameNotFoundException("Usuario no encontrado");

        // Aquí convertimos tu modelo Usuario al formato que entiende Spring Security
        return new User(usuario.getCorreo(), usuario.getContrasena(), new ArrayList<>());
    }
}