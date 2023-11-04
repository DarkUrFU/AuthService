package com.darkurfu.authservice.service.cryptutils;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class HashUtil {

    public String generateHashWithSalt(String str, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return getHash(str, getSalt(salt));
    }

    public String generateHash(String str) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return getHash(str, getSalt(str));
    }

    private String getHash(String str, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(str.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();

        return enc.encodeToString(hash);
    }

    public byte[] getSalt(String str){
        return str.getBytes();
    }
}
