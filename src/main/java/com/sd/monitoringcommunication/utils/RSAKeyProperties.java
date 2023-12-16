package com.sd.monitoringcommunication.utils;

import com.sd.usermanagement.dto.RSAPublicKeyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

@Component
public class RSAKeyProperties {
    private RSAPublicKey publicKey;
    private final WebClient webClient;

    @Autowired
    public RSAKeyProperties(WebClient webClient) {
        this.webClient = webClient;
        this.publicKey = this.getPublicKeyFromUserMicroservice();
    }

    public RSAPublicKey getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    private RSAPublicKey getPublicKeyFromUserMicroservice() {
        RSAPublicKeyDTO publicKeyDTO = this.webClient.get()
                .uri("/auth/publicKey")
                .retrieve()
                .bodyToMono(RSAPublicKeyDTO.class)
                .block();

        if(publicKeyDTO != null) {
            try {
                BigInteger modulus = new BigInteger(1, java.util.Base64.getDecoder().decode(publicKeyDTO.modulus()));
                BigInteger exponent = new BigInteger(1, java.util.Base64.getDecoder().decode(publicKeyDTO.exponent()));
                RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
                KeyFactory factory = KeyFactory.getInstance("RSA");
                return (RSAPublicKey) factory.generatePublic(spec);
            } catch (Exception e) {
                throw new IllegalStateException();
            }
        } else {
            throw new IllegalStateException();
        }
    }
}
