package com.bilel.SpringBoot_TP01.services.implementations;

import com.bilel.SpringBoot_TP01.entities.Token;
import com.bilel.SpringBoot_TP01.entities.User;
import com.bilel.SpringBoot_TP01.repos.TokenRepo;
import com.bilel.SpringBoot_TP01.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepo tokenRepo;
    @Override
    public Token getToken(String token) {
        return tokenRepo.findByToken(token);
    }

    @Override
    public void deleteToken(Long tokenId) {
        tokenRepo.deleteById(tokenId);
    }

    @Override
    public void saveUserVerificationToken(User user, String verificationToken) {
        Token token = new Token(verificationToken, user);
        tokenRepo.save(token);
    }
}
