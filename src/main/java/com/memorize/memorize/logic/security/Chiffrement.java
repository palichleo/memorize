package com.memorize.memorize.logic.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Chiffrement {
    //private final PathManager pathManager = new PathManager();
    private final String ALGORITHM = "AES";
    private SecretKey getKey;
    private byte[] encryptPass;

    /*
     * Usable methods
     */

    public void chiffrerPassword(String password) throws NoSuchAlgorithmException { //Usable everywhere
        getKey = keygen(); //Stock la clée dans une variable "getKey"
        encryptPass = encrypt(password.getBytes(StandardCharsets.UTF_8), getKey); //Password chiffré
    }
    public SecretKey getKey(){
        return getKey;
    }
    public byte[] getPass(){
        return encryptPass;
    }
    public String dechiffrerPassword() {
        try {
            byte[] keyBytes = null; //pathManager.keyReader();
            SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, ALGORITHM);
            Arrays.fill(keyBytes, (byte) 0);
            return null; //decrypt(pathManager.passReader(), key);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * Logique de chiffrement
     */

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
    private String decrypt(byte[] password, SecretKey key) {
        try {
            Cipher cipher = getCipher();
            cipher.init(Cipher.DECRYPT_MODE, key);

            return new String(cipher.doFinal(password), StandardCharsets.UTF_8);
        } catch (Exception e){
            System.err.println("Error Decrypt");
            return "Error";
        }
    }
}