package com.memorize.memorize.logic.security;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class PathManager {
    private final String PASSFILE = "pass.enc"; private final String SETFILE = "settings.cfg";
    private final String PATH = System.getProperty("user.home") + File.separator + "Memorize" + File.separator;
    private String pathOfKey;
    private String originalFileName; //Uniquement lors de l'inscription
    private final Encryption encryption = new Encryption();
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
            Path path = Paths.get(PATH+SETFILE);
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
        //set pref file
        if (!savePref()){System.err.println("Unable to save 'pref.cfg'");return false;}
        return true;
    }
    /*
    Modify the file to insert the key
     */
    private boolean addKeyInLine(){
        int numberOfLines = numberOfLines();
        if (numberOfLines>2){Random random = new Random();numberOfLines = 1 + random.nextInt(numberOfLines - 1);}
        File file = new File(PATH+originalFileName);
        File tempFile = new File(PATH+"temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line; int count = 1;
            while ((line = reader.readLine()) != null) { // Écrire la ligne dans le fichier temporaire
                writer.write(line); writer.newLine();
                if (count == numberOfLines) { // Insérer la nouvelle ligne après la ligne spécifiée
                    SecretKey key = encryption.getKey();
                    byte[] keyBytes = key.getEncoded();
                    writer.write(Base64.getEncoder().encodeToString(keyBytes));
                    writer.newLine();
                    Arrays.fill(keyBytes, (byte) 0);
                    key = null;
                }
                count++;
            }
            if (!file.delete()) {System.out.println("Impossible de supprimer le fichier original"); return false;}
            if (!tempFile.renameTo(file)) {System.out.println("Impossible de renommer le fichier temporaire"); return false;}
            return true;
        } catch (IOException e) {
            System.err.println("Impossible de stocker la clée"); return false;
        }
    }
    /*
    Save the password in "pass.enc"
     */
    private boolean savePassword() {
        byte[] encryptPass = encryption.getPass();
        try {Files.write(Paths.get(PATH + PASSFILE), encryptPass, StandardOpenOption.CREATE); return true;
        } catch (IOException e) {System.err.println("Unable to save Password"); return false;}
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
        File file = new File(PATH+originalFileName);
        if (!file.delete()) {System.out.println("Impossible de supprimer le fichier original"); return false;}
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
    /*
    Find the number of line inside a file
     */
    private int numberOfLines(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PATH + originalFileName));
            int lines = 0;
            try {
                while (reader.readLine() != null) {
                    lines++;
                }
                return lines;
            } catch (IOException e) {
                System.err.println("Impossible to read file on Memorize"); return 0;
            }
        } catch (FileNotFoundException f){
            System.err.println("Impossible to found file on Memorize"); return 0;
        }
    }


    /*


    public byte[] getKeyPref(){
        try {
            return Files.readAllBytes(Paths.get(PATH + SETFILE));
        } catch (IOException e) {
            System.err.println("Impossible de recuperer les preferences");
            return new byte[0];
        }
    }

    public byte[] passReader() {
        try {
            return Files.readAllBytes(Paths.get(PATH + PASSFILE));
        } catch (IOException e) {
            System.err.println("Impossible de recuperer le fichier");
            return new byte[0];
        }
    }
    public byte[] keyReader(){ //Get bytes into KeyFile
        byte[] keyPathByte = getKeyPref(); //La variable contient le chemin vers la clée
        String keyPath = new String(keyPathByte, StandardCharsets.UTF_8);

        //Lecture de la clée grâce au chemin
        try {
            return Files.readAllBytes(Paths.get(keyPath));
        } catch (IOException e) {
            System.err.println("Impossible de recuperer la clée, réassignement nécessaire.");
            return new byte[0]; //réasignement
        }
    }
    */
    public void reassignmentKeyPath(){
        String keyPath = getPath();
        if (keyPath==null){
            System.err.println("Veuillez spécifier un fichier");
        } else {
           // saveKeyPref(keyPath);
        }
    }

}
