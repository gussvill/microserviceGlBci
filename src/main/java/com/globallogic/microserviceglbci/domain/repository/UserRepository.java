package com.globallogic.microserviceglbci.domain.repository;


import com.globallogic.microserviceglbci.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}