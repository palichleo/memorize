package com.memorize.memorize.logic.security;

import com.memorize.memorize.logic.security.Chiffrement;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class PathManager {
    private final String PASSFILE = "pass.enc";
    private final String SETFILE = "settings.cfg";
    private String originalFileName; //Uniquement lors de l'inscription
    private final String PATH = System.getProperty("user.home") + File.separator + "Memorize" + File.separator;
    private final Chiffrement chiffrement = new Chiffrement();
    /*
     * Prend un fichier et le copie dans le path Memorize
     */
    public void inscriptionGetAndCopyFile() throws IOException {
        //Prendre le fichier
        String file = userPathDefine("GET");
        if (file==null){
            System.err.println("Veuillez spécifier un fichier");
        } else {
            Path sourcePath = Paths.get(file);
            String fileName = sourcePath.getFileName().toString();
            Path destinationPath = Paths.get(PATH, fileName);
            //Copier le fichier
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            originalFileName = fileName;
        }
    }
    /*
     * Modifie le fichier pour y inserer la clée
     */
    public void inscriptionSetFile(){
        //On transforme le nombre de ligne en valeur random entre 1 et numberOfLines-1
        int numberOfLines = numberOfLines();
        if (numberOfLines>2){ //Ca n'a pas de sens si le fichier contient moins de 3 lignes
            Random random = new Random();
            numberOfLines = 1+ random.nextInt(numberOfLines - 1);
        }
        File file = new File(PATH+originalFileName);
        File tempFile = new File(PATH+"temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            int count = 1;
            while ((line = reader.readLine()) != null) { // Écrire la ligne dans le fichier temporaire
                writer.write(line);
                writer.newLine();
                if (count == numberOfLines) { // Insérer la nouvelle ligne après la ligne spécifiée
                    SecretKey key = chiffrement.getKey();
                    byte[] keyBytes = key.getEncoded();
                    writer.write(Base64.getEncoder().encodeToString(keyBytes));
                    writer.newLine();
                    Arrays.fill(keyBytes, (byte) 0);
                    key = null;
                }
                count++;
            }
        } catch (IOException e) {
            System.err.println("Impossible de stocker la clée");
        }
        if (!file.delete()) {
            System.out.println("Impossible de supprimer le fichier original");
            return;
        }
        if (!tempFile.renameTo(file)) {
            System.out.println("Impossible de renommer le fichier temporaire");
        }
        String pathOfKey = userPathDefine("save");
        saveKeyPref(pathOfKey);
        //saveKeyFile(pathOfKey, key);
    }
    private void savePassword(byte[] encryptPass) { //Fichier du mdp + password
        try { Files.createDirectories(Paths.get(PATH)); } catch (IOException e1) {System.err.println("Unable to save Password Directories");}
        try {
            Files.write(Paths.get(PATH + PASSFILE), encryptPass, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Unable to save Password");
        }
    }
    private void saveKeyFile(String pathOfKey, SecretKey key){
        try {
            Files.write(Paths.get(pathOfKey), key.getEncoded(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Unable to save Key");
        }
    }
    /*
     * Creer et paramettre les fichier pass et settings
     */
    public void inscriptionSetPassAndPref(){
        byte[] encryptPass = chiffrement.getPass();

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
    }
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
    public void reassignmentKeyPath(){
        String keyPath = userPathDefine("GET"); //Appel d'un stage permettant de recuperer la clée
        //On save le path
        if (keyPath==null){
            System.err.println("Veuillez spécifier un fichier");
        } else {
            saveKeyPref(keyPath);
        }
    }
    public String userPathDefine(String saveOrGet){ //Users define the path of the key
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Fichier contenant votre clée de chiffrement");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichier wav (*.wav)", "*.wav");
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
    /*
    Trouve le nombre de lignes présent dans un fichier
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
                return 0;
            }
        } catch (FileNotFoundException f){
            return 0;
        }
    }

    private void saveKeyPref(String pathOfKey) { //Preference de l'utilisateur concernant le chemin de la clée
        try {
            Files.write(Paths.get(PATH + SETFILE), pathOfKey.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Unable to save Preferences");
        }
    }
}
