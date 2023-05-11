package com.globallogic.microserviceglbci.service;

import com.globallogic.microserviceglbci.domain.entity.Usuario;
import com.globallogic.microserviceglbci.domain.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * La clase `UsuarioQueryServiceImpl` es una implementación de la interfaz `IUsuarioQueryService`, que define métodos para realizar consultas y operaciones en la base de datos que almacena objetos `Usuario`.
 */
@Service
public class UsuarioQueryServiceImpl implements IUsuarioQueryService {

    @Autowired
    private IUsuarioRepository IUsuarioRepository;

    @Override
    public Optional<Usuario> getUserByEmail(String email) {
        return IUsuarioRepository.findByEmailContaining(email);
    }

    @Override
    public Usuario getUserByEmail(String email, String nullValue) {
        return IUsuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> getUsers() {
        return IUsuarioRepository.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {
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
}