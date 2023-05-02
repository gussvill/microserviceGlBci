package com.globallogic.microserviceglbci.domain.repository;

import com.globallogic.microserviceglbci.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByEmailContaining(String email);

    Optional<Usuario> findOneByName(String usuario);

    Usuario findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.token = :newToken WHERE u.email = :email")
    void updateToken(@Param("email") String email, @Param("newToken") String newToken);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.lastLogin = :date WHERE u.email = :email")
    void updateLastLogin(@Param("email") String email, @Param("date") String date);

}