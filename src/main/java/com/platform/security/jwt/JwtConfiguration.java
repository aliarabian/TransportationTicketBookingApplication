package com.platform.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

// Citation:
// https://medium.com/swlh/stateless-jwt-authentication-with-spring-boot-a-better-approach-1f5dbae6c30f#id_token=eyJhbGciOiJSUzI1NiIsImtpZCI6IjQ4NmYxNjQ4MjAwNWEyY2RhZjI2ZDkyMTQwMThkMDI5Y2E0NmZiNTYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2NTMxOTEyNTAsImF1ZCI6IjIxNjI5NjAzNTgzNC1rMWs2cWUwNjBzMnRwMmEyamFtNGxqZGNtczAwc3R0Zy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjExNjcwODIxOTUyMTg3NDc5NTkyNCIsImVtYWlsIjoiYWxpYXJhYmlhbi5kZXZAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF6cCI6IjIxNjI5NjAzNTgzNC1rMWs2cWUwNjBzMnRwMmEyamFtNGxqZGNtczAwc3R0Zy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsIm5hbWUiOiJBbGkgQXJhYmlhbiIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS0vQU9oMTRHalJDcGF6akYxNEs1VmlwU2dRVXIyVFVfNkNpeEpUdi1PVXZGRm89czk2LWMiLCJnaXZlbl9uYW1lIjoiQWxpIiwiZmFtaWx5X25hbWUiOiJBcmFiaWFuIiwiaWF0IjoxNjUzMTkxNTUwLCJleHAiOjE2NTMxOTUxNTAsImp0aSI6IjU5NDk4N2E5MzM2NGYzM2M4ODMwNTkzZjEwNjRjMjNjZmZjYzZiNmQifQ.N7979NfK_fLVNhfaVZd0fr8nzPJJoFxxC8DabqK1aJzQsjQVJCqZSLzSfWvW9JV97Rlqj_BIGZRsaP3DaCOLBYv2m6l_ZRlq9uHhZL-n7D9k_m_o5qvbBnCwyCLcQOBLdhvpYtkvTzMOWV-_OEYq03x4ZeQhtj53-MTooQsaraCFrbyTj42g2HWpBm_v9KdiM1kWuVHCggLOuLUM6g3gYqk50hiQ2N-NWH3j4PIZTeTnn0Dan0FzlW-eZQQmUw3HvlJfLNqeU4xt5jknxLvbmCqtKni0YZsAWA3Y7EBimFc-nznyMUbxLwqVMhpQZHLVQH_Gn2QTnhE_VlLaeyRSlA

@Configuration
public class JwtConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtConfiguration.class.getName());
    @Value("${app.security.jwt.keystore-location}")
    private String keyStorePath;

    @Value("${app.security.jwt.keystore-password}")
    private String keyStorePassword;

    @Value("${app.security.jwt.key-alias}")
    private String keyAlias;

    @Value("${app.security.jwt.private-key-passphrase}")
    private String privateKeyPassphrase;

    @Bean
    public KeyStore keyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStorePath);
            keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
            return keyStore;
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {

            LOGGER.error("Unable to load keystore: {}", keyStorePath, e);
        }
        throw new IllegalArgumentException("Unable to load keystore");
    }

    @Bean
    public RSAPrivateKey jwtSigningKey(KeyStore keyStore) {
        try {
            Key key = keyStore.getKey(keyAlias, privateKeyPassphrase.toCharArray());
            if (key instanceof RSAPrivateKey) {
                return (RSAPrivateKey) key;
            }
        } catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
            LOGGER.error("Unable to load private key from keystore: {}", keyStorePath, e);
        }

        throw new IllegalArgumentException("Unable to load private key");
    }

    @Bean
    public RSAPublicKey jwtValidationKey(KeyStore keyStore) {
        try {
            Certificate certificate = keyStore.getCertificate(keyAlias);
            PublicKey publicKey = certificate.getPublicKey();

            if (publicKey instanceof RSAPublicKey) {
                return (RSAPublicKey) publicKey;
            }
        } catch (KeyStoreException e) {
            LOGGER.error("Unable to load private key from keystore: {}", keyStorePath, e);
        }

        throw new IllegalArgumentException("Unable to load RSA public key");
    }

    @Bean
    public JwtDecoder jwtDecoder(RSAPublicKey rsaPublicKey) {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey)
                               .signatureAlgorithm(SignatureAlgorithm.RS512)
                               .build();
    }
}
