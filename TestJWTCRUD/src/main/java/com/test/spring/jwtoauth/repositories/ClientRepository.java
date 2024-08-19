package com.test.spring.jwtoauth.repositories;

import com.test.spring.jwtoauth.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByCreatedAtAfter(LocalDateTime date);
    List<Client> findByCreatedAtBefore(LocalDateTime date);
}
