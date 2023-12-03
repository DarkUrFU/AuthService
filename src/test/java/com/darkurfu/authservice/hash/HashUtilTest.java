package com.darkurfu.authservice.hash;


import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.service.cryptutils.HashUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;


public class HashUtilTest {

    private static HashUtil hashUtil = new HashUtil();


    @Nested
    class UserHash{
        private static final User user = new User("login", "password");
        private static final User badUser = new User("login", "badPassword");
        private static final User otherUser = new User("otherLogin", "otherPassword");


        static private String generatedHash;

        @BeforeAll
        static void generateData() throws NoSuchAlgorithmException, InvalidKeySpecException {

            generatedHash = hashUtil.generateHash(user.getPassword(), user.getSalt());
        }

        @Test
        void generateUserHash(){
            System.out.printf("Generated: %s \n", generatedHash);
        }

        @Test
        void compareGoodHashWithSalt() throws NoSuchAlgorithmException, InvalidKeySpecException {

            String hash = hashUtil.generateHash(user.getPassword(), user.getSalt());

            assertEquals(hash, generatedHash);

            System.out.printf("old: %s | new: %s \n", generatedHash, hash);
        }

        @Test
        void compareBadHashWithSalt() throws NoSuchAlgorithmException, InvalidKeySpecException {
            String hash = hashUtil.generateHash(badUser.getPassword(), badUser.getSalt());

            assertNotEquals(hash, generatedHash);

            System.out.printf("good: %s | bad: %s \n", generatedHash, hash);
        }

        @Test
        void compareDiffUsersHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
            String hash = hashUtil.generateHash(otherUser.getPassword(), otherUser.getSalt());

            assertNotEquals(hash, generatedHash);

            System.out.printf("old: %s | new: %s \n", generatedHash, hash);
        }
    }



    @Nested
    class Hash{

        /*
        @Test
        void compareGoodHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
            String uuid = UUID.randomUUID().toString();

            String hash1 = hashUtil.generateHash(uuid);
            String hash2 = hashUtil.generateHash(uuid);

            assertEquals(hash1, hash2);

            System.out.printf("hash1: %s | hash2: %s \n", hash1, hash2);
        }*/

        /*
        @Test
        void compareBadHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
            String uuid1 = UUID.randomUUID().toString();
            String uuid2 = UUID.randomUUID().toString();

            String hash1 = hashUtil.generateHash(uuid1);
            String hash2 = hashUtil.generateHash(uuid2);

            assertNotEquals(hash1, hash2);

            System.out.printf("hash1: %s | hash2: %s \n", hash1, hash2);
        }*/
    }
}
