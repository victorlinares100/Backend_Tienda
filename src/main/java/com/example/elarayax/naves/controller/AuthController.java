package com.example.elarayax.naves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.elarayax.naves.repository.UsuarioRepository;
import com.example.elarayax.naves.dto.LoginRequest;
import com.example.elarayax.naves.model.Usuario;
import com.example.elarayax.naves.security.JwtUtil; // Asegúrate de tener esta clase
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo());

        if (usuario == null || !passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // GENERACIÓN DEL TOKEN
        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("nombre", usuario.getNombre()); // Usamos 'nombre' para que coincida con user.nombre
        response.put("rol", usuario.getRol());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo().trim())) {
            return ResponseEntity.badRequest().body("El correo ya está en uso.");
        }
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuario.setRol("Cliente");
        usuario.setCorreo(usuario.getCorreo().trim());

        try {
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar.");
        }
    }
}