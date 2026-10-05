package com.agencia.viagens.controller;

import com.agencia.viagens.dto.AuthResponse;
import com.agencia.viagens.dto.LoginRequest;
import com.agencia.viagens.dto.RegisterRequest;
import com.agencia.viagens.model.Usuario;
import com.agencia.viagens.repository.UsuarioRepository;
import com.agencia.viagens.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username já cadastrado");
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nome(request.getNome())
                .email(request.getEmail())
                .perfil(request.getPerfil())
                .build();

        usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, usuario.getUsername(), usuario.getPerfil().name()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtUtil.generateToken((UserDetails) usuario);
        return ResponseEntity.ok(new AuthResponse(token, usuario.getUsername(), usuario.getPerfil().name()));
    }
}
