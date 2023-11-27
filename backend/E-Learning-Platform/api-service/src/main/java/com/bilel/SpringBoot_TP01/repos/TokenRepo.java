package com.bilel.SpringBoot_TP01.repos;

import com.bilel.SpringBoot_TP01.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<Token, Long> {
    Token findByToken(String token);
}
