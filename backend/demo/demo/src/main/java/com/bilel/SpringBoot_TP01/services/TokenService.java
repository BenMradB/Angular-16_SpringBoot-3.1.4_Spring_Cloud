package com.bilel.SpringBoot_TP01.services;

import com.bilel.SpringBoot_TP01.entities.Token;
import com.bilel.SpringBoot_TP01.entities.User;

public interface TokenService {
    Token getToken(String token);

    void deleteToken(Long tokenId);
    void saveUserVerificationToken(User user, String verificationToken);

}
