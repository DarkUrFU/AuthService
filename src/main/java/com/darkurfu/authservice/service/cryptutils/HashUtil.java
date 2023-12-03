package com.darkurfu.authservice.service.cryptutils;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Logger;

@Component
public class HashUtil {

    public String generateHash(String str, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return getHash(str, stringToByte(salt));
    }

    private String getHash(String str, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(str.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();

        return enc.encodeToString(hash);
    }

    public byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }

    public String generateSaltAsString(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return bytetoString(bytes);
    }

    public String bytetoString(byte[] byteArray) {
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public byte[] stringToByte(String str) {

        return Base64.getDecoder().decode(str);

    }
}
