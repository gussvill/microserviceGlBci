package com.bci.microservice.domain.service;

import com.bci.microservice.persistence.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements com.bci.microservice.domain.repository.UsuarioRepository {

    @Autowired
    private com.bci.microservice.persistence.jpa.UsuarioJpaRepository UsuarioJpaRepository;

    @Override
    public Optional<Usuario> getUserByEmail(String email) {
        return UsuarioJpaRepository.findByEmailContaining(email);
    }

    @Override
    public Usuario getUserByEmail(String email, String nullValue) {
        return UsuarioJpaRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> getUsers() {
        return UsuarioJpaRepository.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {
        return UsuarioJpaRepository.save(usuario);
    }

    @Override
    public void updateToken(String email, String newToken) {
        UsuarioJpaRepository.updateToken(email, newToken);
    }

    @Override
    public void updateLastLogin(String email, String date) {
        UsuarioJpaRepository.updateLastLogin(email, date);
    }
}