package com.memorize.memorize.logic;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class Chiffrement {
    private final PathManager pathManager = new PathManager();
    private final String ALGORITHM = "AES";

    /*
     * Usable methods
     */

    public void chiffrerPassword(String password) throws NoSuchAlgorithmException { //Usable everywhere

        SecretKey getKey = keygen(); //Stock la clée dans une variable "getKey"
        byte[] encryptPass = encrypt(password.getBytes(StandardCharsets.UTF_8), getKey); //Password chiffré
        pathManager.savePaths(getKey, encryptPass); //Stock la clée et le mdp dans un fichier externe
    }
    public String dechiffrerPassword() {
        try {
            SecretKey key = new SecretKeySpec(pathManager.keyReader(), 0, pathManager.keyReader().length, ALGORITHM);
            return decrypt(pathManager.passReader(), key);
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