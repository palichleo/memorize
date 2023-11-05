package com.memorize.memorize.logic.security;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class PathManager {
    private final String PASSFILE = "pass.enc"; private final String SETFILE = "settings.cfg";
    private final String BDDPASS = "bddpass.enc"; private final String BDDNAME = "bddname.enc"; private final String BDDUSER = "bdduser.enc";
    private final String PATH = System.getProperty("user.home") + File.separator + "Memorize" + File.separator;
    private String pathOfKey;
    private String originalFileName; File fileInMemorize; //Uniquement lors de l'inscription
    private final Encryption encryption = new Encryption();
    private final SecureRandom secureRandom = new SecureRandom();
    public PathManager(){
        Path path = Paths.get(PATH);
        if (!Files.exists(path)){
            try {Files.createDirectories(path);
            } catch (IOException e) {System.err.println("Unable to save Directory");}
        }
    }
    /**
     *Verify inscription
     */
    public boolean inscritVerif(){
        try {
            Path path = Paths.get(PATH+PASSFILE);
            return Files.exists(path);
        } catch (Exception e) {
            return false;
        }
    }
    /**
     *Encrypt password
     */
    public boolean encryptPass(String password){
        try {encryption.encryptPassword(password);return true;}
        catch (NoSuchAlgorithmException e){System.err.println("Unable to encrypt password"); return false;}
    }
    /**
     * Take the file and copy it in Memorize path
     * Modify the file to insert the key
     */
    public boolean inscriptionGetCopyAdd() {
        //Get file
        String file = getPath();
        if (file==null){System.err.println("Choose a file");return false;}
        //Copy file
        try {
            Path sourcePath = Paths.get(file);
            originalFileName = sourcePath.getFileName().toString();
            Path destinationPath = Paths.get(PATH, originalFileName);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){System.err.println("Copy failed"); return false;}
        //Add key in line
        if (!addKeyInLine()){System.err.println("Impossible to add the key line");return false;}
        return true;
    }
    /**
     * Save the password in "pass.enc"
     * Ask where to put the key file
     * Create "pref.cfg" file
     */
        public boolean inscriptionSetFiles(){
        //set pass file
        if (!savePassword()){System.err.println("Unable to save 'pass.enc' file");return false;}
        //ask where to put the key
        if (!saveKey()){System.err.println("Unable to save the key");return false;}
        //Delete original key
        if (!deleteAndRename()){System.err.println("Unable to delete original key");return false;}
        //set pref file
        if (!savePref()){System.err.println("Unable to save 'pref.cfg'");return false;}
        return true;
    }
    /**
     *Decrypt the password with the key
     */
    public String decrypt(){
        byte[] keyBytes = keyReader();
        try {
            SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, encryption.getALGORITHM());
            return encryption.decrypt(passReader(), key);
        } finally {
            secureRandom.nextBytes(keyBytes);
        }
    }
    /**
     *Reassignment of the key path
     */
    public void reassignmentKeyPath(){
        pathOfKey = getPath();
        if (pathOfKey==null){
            System.err.println("Please specify a file");
        } else {
            savePref();
        }
    }

    /**
     * Pour un fichier donné, cherche une chaine de byte et la décrypte avec la clée
     * @param file 0 : name ;
     *             1 : pass ;
     *             2 : username
     * @return String : Name or Password or UserName
     */
    public String getInformation(int where, int file) throws FileTooSmallException {
        File bdd;
        if (file==0){bdd = new File(PATH + BDDNAME);}
        else if (file==1){bdd = new File(PATH + BDDPASS);}
        else {bdd = new File(PATH + BDDUSER);}

        byte[] keyBytes = keyReader();

        try {
            byte[] nameOrPassBytes = decryptBDD(where, bdd);
            SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, encryption.getALGORITHM());
            return encryption.decrypt(nameOrPassBytes, key);
        } catch (ReadImpossible e){
            return null;
        } finally {
        secureRandom.nextBytes(keyBytes);
        }
    }
    private byte[] decryptBDD(int where, File file) throws FileTooSmallException, ReadImpossible {
            byte[] passwordBytes = new byte[32]; // Pour AES 256

            try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
                long fileLength = randomAccessFile.length();
                long startPosition = fileLength - (where * 32L);

                // Vérifier si la position de départ est valide
                if (startPosition >= 0) {
                    randomAccessFile.seek(startPosition);
                    randomAccessFile.readFully(passwordBytes);
                    return passwordBytes;
                } else {
                    throw new FileTooSmallException("too small");
                }
            } catch (IOException e) {
                throw new ReadImpossible("Impossible de lire le fichier");
            }
    }
    /*
    Modify the file to insert the key
     */
    private boolean addKeyInLine(){
        fileInMemorize = new File(PATH+originalFileName);

        SecretKey key = encryption.getKey();
        byte[] keyBytes = key.getEncoded();

        try (RandomAccessFile file = new RandomAccessFile(fileInMemorize, "rw")) {
            file.seek(file.length());
            file.write(keyBytes);
        } catch (IOException e) {
            System.err.println("Unable to store key"); return false;
        } finally {
            secureRandom.nextBytes(keyBytes);
        }
        return true;
    }
    private boolean deleteAndRename(){
        if (!fileInMemorize.delete()) {System.err.println("Impossible de supprimer le fichier original");return false;}
        return true;
    }
    /*
    Save the password in "pass.enc"
     */
    private boolean savePassword() {
        byte[] encryptPass = encryption.getPass();
        try {
            Files.write(Paths.get(PATH + PASSFILE), encryptPass, StandardOpenOption.CREATE);
            return true;
        } catch (IOException e) {
            System.err.println("Unable to save Password"); return false;
        }
        finally {
            secureRandom.nextBytes(encryptPass);
        }
    }
    /*
    Ask where to put the key file
     */
    private boolean saveKey(){
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Folder in which your key will be saved");
        File folder = directoryChooser.showDialog(stage);
        stage.close();
        if(folder==null) return false;
        try {
            Path sourcePath = Paths.get(PATH+originalFileName);
            Path destinationPath = Paths.get(folder.getAbsolutePath(), originalFileName);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){System.err.println("Copy failed"); return false;}
        pathOfKey = Paths.get(folder.getAbsolutePath(), originalFileName).toString();
        return true;
    }
    /*
    Create "pref.cfg" file
     */
    private boolean savePref(){
        try {
            Files.write(Paths.get(PATH + SETFILE), pathOfKey.getBytes(), StandardOpenOption.CREATE);
            return true;
        } catch (IOException e) {
            System.err.println("Unable to save Preferences");
            return false;
        }
    }
    /*
    Find the key file
     */
    private String getPath(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Encrypted file key");
        File file = fileChooser.showOpenDialog(stage);
        stage.close();
        if(file==null) return null;
        return file.getAbsolutePath();
    }
    private byte [] keyReader(){
        if (!getKeyPath()) {
            System.err.println("Impossible to recover the key preference, reassignment necessary.");
            reassignmentKeyPath();
            return new byte[0];
        }
        //Lecture de la clée grâce au chemin
        int bytesCount = 32; //32 bits pour AES 256
        byte[] bytes = new byte[bytesCount];

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(pathOfKey, "r")) {
            long length = randomAccessFile.length();
            if (length >= bytesCount) {
                randomAccessFile.seek(length - bytesCount);
                randomAccessFile.readFully(bytes);
                return bytes;
            } else { // Le fichier est plus petit que le nombre de bytes demandés
                System.err.println("Le fichier est trop petit pour lire " + Arrays.toString(bytes) + " bytes.");
                reassignmentKeyPath();
                return new byte[0];
            }
        } catch (IOException e) {
            System.err.println("Impossible to recover the key, reassignment necessary. " + e.getMessage());
            reassignmentKeyPath();
            return new byte[0];
        }
    }
    private boolean getKeyPath(){
        try {
            byte[] keyPathByte = Files.readAllBytes(Paths.get(PATH + SETFILE));
            pathOfKey = new String(keyPathByte, StandardCharsets.UTF_8);
            secureRandom.nextBytes(keyPathByte);
            return true;
        } catch (IOException e) {
            System.err.println("Unable to retrieve preferences");
            return false;
        }
    }
    private byte[] passReader() {
        try {
            return Files.readAllBytes(Paths.get(PATH + PASSFILE));
        } catch (IOException e) {
            System.err.println("Unable to recover pass file");
            return new byte[0];
        }
    }
    /*
    Exceptions
     */
    public static class FileTooSmallException extends Exception {
        public FileTooSmallException(String message) {
            super(message);
        }
    }
    static class ReadImpossible extends Exception {
        public ReadImpossible(String message) {
            super(message);
        }
    }
}
