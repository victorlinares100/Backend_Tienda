package com.example.elarayax.naves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.elarayax.naves.repository.UsuarioRepository;
import com.example.elarayax.naves.dto.LoginRequest;
import com.example.elarayax.naves.model.Usuario;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {

        // Verifica si el correo ya está en uso, si lo está lanza un error
        if (usuarioRepository.existsByCorreo(usuario.getCorreo().trim())) {
            return ResponseEntity.badRequest().body("El correo ya está en uso.");
        }
        //Esto encripta la contraseña antes de guardarla
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        // Asignar el rol "Cliente" al cualquier nuevo usuario por default
        usuario.setRol("Cliente");
        // Asegurar que el correo se guarde sin espacios
        usuario.setCorreo(usuario.getCorreo().trim());
        
        try {
            // Guardar el usuario en la base de datos
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("El correo ya está en uso.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo());

        if (usuario == null || !passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");    
        }
        return ResponseEntity.ok(usuario);
    }



}
