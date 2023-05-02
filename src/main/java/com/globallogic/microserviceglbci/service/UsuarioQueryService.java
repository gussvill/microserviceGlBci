package com.globallogic.microserviceglbci.service;

import com.globallogic.microserviceglbci.domain.entity.Usuario;
import com.globallogic.microserviceglbci.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioQueryService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> getUserByEmail(String email) {
        return usuarioRepository.findByEmailContaining(email);
    }

    public Usuario getUserByEmail(String email, String nullValue) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> getUsers() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void updateToken(String email, String newToken) {
        usuarioRepository.updateToken(email, newToken);
    }

    public void updateLastLogin(String email, String date) {
        usuarioRepository.updateLastLogin(email, date);
    }
}
