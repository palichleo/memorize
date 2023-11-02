package com.memorize.memorize.logic.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Encryption {
    private final String ALGORITHM = "AES";
    private SecretKey getKey;
    private byte[] encryptPass;
    protected void encryptPassword(String password) throws NoSuchAlgorithmException {
        getKey = keygen();
        encryptPass = encrypt(password.getBytes(StandardCharsets.UTF_8), getKey);
    }
    protected SecretKey getKey(){
        return getKey;
    }
    protected byte[] getPass(){
        return encryptPass;
    }
    protected String getALGORITHM(){
        return ALGORITHM;
    }
    protected String decrypt(byte[] password, SecretKey key) {
        try {
            Cipher cipher = getCipher();
            cipher.init(Cipher.DECRYPT_MODE, key);

            return new String(cipher.doFinal(password), StandardCharsets.UTF_8);
        } catch (Exception e){
            System.err.println("Error Decrypt");
            return "Error";
        }
    }
    private SecretKey keygen() throws NoSuchAlgorithmException { //Genere une clée de chiffrement aléatoire
        KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
        keygen.init(256);

        return keygen.generateKey();
    }
    private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(ALGORITHM);
    }
    private byte[] encrypt(byte[] password, SecretKey key) { //Algorithme de chiffrement
        try {
            Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(password);
        } catch (Exception e) {
            System.err.println("Chiffrement impossible");
            return new byte[0];
        }
    }
}