package com.cyberschnitzel.Util;

import com.cyberschnitzel.Domain.Exceptions.HashingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.UUID;

public class Hasher {
    private static final String algorithm = "AES";
    private static final byte[] keyValue =
            new byte[]{'r', 'G', 'Y', 'e', 'A', 'a', 'Z', 'Z', 'q', 'D', 'd', 'O', 'Y', 'm', 'R', 'k'};
    private static String SALT;
    private static String PEPPER;

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    public static String encrypt(String data) throws HashingException {
        try {
            // Load salt and pepper if not already loaded
            loadSaltAndPepper();

            // Salt and pepper data
            data = SALT + data + PEPPER;

            // Hash the data
            Key key = generateKey();
            Cipher c = Cipher.getInstance(algorithm);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes());
            return new BASE64Encoder().encode(encVal);
        } catch (Exception e) {
            throw new HashingException("Failed to encrypt data: " + data + ": " + e.getMessage());
        }
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData) throws Exception {
        // Load salt and pepper if not already loaded
        loadSaltAndPepper();

        Key key = generateKey();
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);

        // Desalt and depepper data and return it
        return new String(decValue).replace(SALT, "").replace(PEPPER, "");
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() {
        return new SecretKeySpec(keyValue, algorithm);
    }

    public static String getToken() throws HashingException {
        return encrypt(UUID.randomUUID().toString());
    }

    public static void loadSaltAndPepper() {
        if (SALT == null || PEPPER == null) {
            SALT = Config.getHasherSalt();
            PEPPER = Config.getHasherPepper();
        }
    }
}
