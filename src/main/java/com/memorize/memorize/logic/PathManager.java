package com.memorize.memorize.logic;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PathManager {
    private final String PASSFILE = "pslocal.txt";
    private final String SETFILE = "settings.txt";
    private final String PATH = System.getProperty("user.home") + File.separator + "Memorize" + File.separator;
    /*
     * Path control
     */
    public void savePaths(SecretKey key, byte[] encryptPass) { //Usable generic method
        Path pass = Paths.get(PATH + PASSFILE);
        Path pref = Paths.get(PATH + SETFILE);
        if (Files.exists(pass)){
            try {
                Files.delete(pass);
            } catch (IOException ePass) {
                System.err.println("Impossible de supprimer le fichier pass");
            }
        }
        if (Files.exists(pref)){
            try {
                Files.delete(pref);
            } catch (IOException ePref) {
                System.err.println("Impossible de supprimer le fichier pref");
            }
        }
        savePassword(encryptPass);
        saveKey(key);
    }
    private void savePassword(byte[] encryptPass) { //Fichier du mdp + password
        try { Files.createDirectories(Paths.get(PATH)); } catch (IOException e1) {System.err.println("Unable to save Password Directories");}
        try {
            Files.write(Paths.get(PATH + PASSFILE), encryptPass, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Unable to save Password");

        }
    }
    private void saveKey(SecretKey key) { //Fichier de la clée + preferences
        String pathOfKey = userPathDefine("KEY", "save");
        saveKeyPref(pathOfKey);
        saveKeyFile(pathOfKey, key);
    }
    private void saveKeyFile(String pathOfKey, SecretKey key){
        try {
            Files.write(Paths.get(pathOfKey), key.getEncoded(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Unable to save Key");
        }
    }
    private void saveKeyPref(String pathOfKey) { //Preference de l'utilisateur concernant le chemin de la clée
        try {
            Files.write(Paths.get(PATH + SETFILE), pathOfKey.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Unable to save Preferences");
        }
    }
    public byte[] getKeyPref(){
        try {
            return Files.readAllBytes(Paths.get(PATH + SETFILE));
        } catch (IOException e) {
            System.err.println("Impossible de recuperer les preferences");
            return new byte[0];
        }
    }

    public byte[] passReader() { //Get bytes into passFile
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
            Path keyFilePath = Paths.get(keyPath);
            return Files.readAllBytes(keyFilePath);
        } catch (IOException e) {
            System.err.println("Impossible de recuperer la clée, réassignement nécessaire.");

            return reassignmentKeyPath(); //réasignement
        }
    }
    public byte[] reassignmentKeyPath(){
        String keyPath = userPathDefine("KEY", "GET"); //Appel d'un stage permettant de recuperer la clée
        //On save le path
        saveKeyPref(keyPath);
        Path keyFilePath = Paths.get(keyPath);
        try {
            return Files.readAllBytes(keyFilePath);
        } catch (IOException e) {
            System.err.println("Erreur critique lors du réasignement");
            return new byte[0];
        }
    }
    public String userPathDefine(String KEYorPASSorPREF, String saveOrGet){ //Users define the path of the key
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        if (KEYorPASSorPREF.equals("PASS")) {
            fileChooser.setTitle("Fichier 'pslocal.txt' contenant votre mot de passe chiffré");
        } else if (KEYorPASSorPREF.equals("KEY")){
            fileChooser.setTitle("Fichier contenant votre clée de chiffrement");
        } else {
            fileChooser.setTitle("Fichier 'settings.txt' contenant vos paramettres");
        }
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichier texte (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

            File file;
        if (saveOrGet.equals("save")){
            file = fileChooser.showSaveDialog(stage);
        } else {
            file = fileChooser.showOpenDialog(stage);
        }

        stage.close();
        if(file != null) return file.getAbsolutePath();
        return null;
    }
    public boolean inscritVerif(){
        try {
            Path path = Paths.get(PATH+SETFILE);
            return Files.exists(path);
        } catch (Exception e) {
            return false;
        }
    }
}
