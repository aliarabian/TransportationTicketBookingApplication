package com.platform.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecureCsrfTokenGenerator implements CsrfTokenGenerator {
    private static final int RANDOM_BYTE_SIZE = 20;

    @Override
    public String generate() {
        SecureRandom random;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // TODO fix exception handling
            return null;
        }

        byte[] randomBytes = new byte[RANDOM_BYTE_SIZE];
        random.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }
}
