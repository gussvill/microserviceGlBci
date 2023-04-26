package com.globallogic.microserviceglbci.domain.repository;


import com.globallogic.microserviceglbci.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNameContaining(String name);

    Optional<Usuario> findOneByName(String usuario);

}