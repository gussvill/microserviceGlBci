package com.bci.microservice.service;

import com.bci.microservice.entity.Usuario;
import com.bci.microservice.repository.UsuarioJpaRepository;
import com.bci.microservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UsuarioRepository {

    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<Usuario> getUserByEmail(String email) {
        return usuarioJpaRepository.findByEmailContaining(email);
    }

    @Override
    public Usuario getUserByEmail(String email, String nullValue) {
        return usuarioJpaRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> getUsers() {
        return usuarioJpaRepository.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioJpaRepository.save(usuario);
    }

    @Override
    public void updateToken(String email, String newToken) {
        usuarioJpaRepository.updateToken(email, newToken);
    }

    @Override
    public void updateLastLogin(String email, String date) {
        usuarioJpaRepository.updateLastLogin(email, date);
    }

    @Override
    public String passwordEncoder(String userPassword) {
        return passwordEncoder.encode(userPassword);
    }
}