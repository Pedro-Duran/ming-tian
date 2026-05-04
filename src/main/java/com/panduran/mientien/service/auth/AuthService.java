package com.panduran.mientien.service.auth;

import com.panduran.mientien.dto.AuthDTO;
import com.panduran.mientien.entity.Usuario;
import com.panduran.mientien.repository.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthDTO.Response register(AuthDTO.Request request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuarioRepository.save(usuario);
        return new AuthDTO.Response(jwtService.generateToken(usuario.getEmail()));
    }

    public AuthDTO.Response login(AuthDTO.Request request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        return new AuthDTO.Response(jwtService.generateToken(usuario.getEmail()));
    }
}
