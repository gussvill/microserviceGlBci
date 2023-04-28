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

    public List<Usuario> getUsers() {
        return usuarioRepository.findAll();
    }

    public void deleteUsers() {
        usuarioRepository.deleteAll();
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
