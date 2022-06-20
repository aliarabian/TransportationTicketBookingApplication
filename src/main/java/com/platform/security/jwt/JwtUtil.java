package com.platform.security.jwt;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private final RSAPublicKey rsaPublicKey;
    private final RSAPrivateKey rsaPrivateKey;

    public JwtUtil(RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
        this.rsaPublicKey = rsaPublicKey;
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String creatJwtFromUserDetails(UserDetails userDetails) {

        JwtClaims jwtClaims = createJwt(userDetails.getUsername(), userDetails.getAuthorities());
        try {
            return createJws(jwtClaims);
        } catch (JoseException e) {
            throw new RuntimeException(e);
        }
    }

    private String createJws(JwtClaims jwtClaims) throws JoseException {
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(jwtClaims.toJson());
        jws.setAlgorithmHeaderValue("RS512");
        jws.setKey(rsaPrivateKey);
        jws.setDoKeyValidation(false);

        return jws.getCompactSerialization();
    }

    private JwtClaims createJwt(String username, Collection<? extends GrantedAuthority> authorities) {
        JwtClaims claims = new JwtClaims();
        claims.setIssuer("Issuer");  // who creates the token and signs it
        claims.setAudience("Audience"); // to whom the token is intended to be sent
        claims.setExpirationTimeMinutesInTheFuture(30); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject(username); // the subject/principal is whom the token is about
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        claims.setStringListClaim("roles", roles);
        return claims;
    }
}
