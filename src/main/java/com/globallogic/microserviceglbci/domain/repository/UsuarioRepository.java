package com.globallogic.microserviceglbci.domain.repository;


import com.globallogic.microserviceglbci.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    List<Usuario> findByEmailContaining(String email);

    Optional<Usuario> findOneByName(String usuario);

}