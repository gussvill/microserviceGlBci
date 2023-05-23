package com.bci.microservice.service;

import com.bci.microservice.interfaces.IUsuario;
import com.bci.microservice.interfaces.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuario {

    @Autowired
    private IUsuarioRepository IUsuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<com.bci.microservice.entity.Usuario> getUserByEmail(String email) {
        return IUsuarioRepository.findByEmailContaining(email);
    }

    @Override
    public com.bci.microservice.entity.Usuario getUserByEmail(String email, String nullValue) {
        return IUsuarioRepository.findByEmail(email);
    }

    @Override
    public List<com.bci.microservice.entity.Usuario> getUsers() {
        return IUsuarioRepository.findAll();
    }

    @Override
    public com.bci.microservice.entity.Usuario save(com.bci.microservice.entity.Usuario usuario) {
        return IUsuarioRepository.save(usuario);
    }

    @Override
    public void updateToken(String email, String newToken) {
        IUsuarioRepository.updateToken(email, newToken);
    }

    @Override
    public void updateLastLogin(String email, String date) {
        IUsuarioRepository.updateLastLogin(email, date);
    }

    @Override
    public String passwordEncoder(String userPassword) {
        return passwordEncoder.encode(userPassword);
    }
}