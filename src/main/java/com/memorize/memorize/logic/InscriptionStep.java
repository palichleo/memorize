package com.memorize.memorize.logic;

import com.memorize.memorize.logic.security.Chiffrement;
import com.memorize.memorize.logic.security.PathManager;
import com.memorize.memorize.stages.InscriptionStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.security.NoSuchAlgorithmException;

public class InscriptionStep {
    private final int LENGTH = 1;
    private String password;
    private PathManager pathManager = new PathManager();
    public void verification(PasswordField passwordLogin, PasswordField passwordVerifier,
                             Button buttonHide, Button buttonShow,
                             AnchorPane paneShow, AnchorPane paneHide) {
        if (passwordLogin.getText().equals(passwordVerifier.getText())){
            if (passwordVerifier.getLength()>= LENGTH) {
                try {
                    password = String.valueOf(passwordVerifier.getText());
                    clearAndSetPrompt(passwordLogin, "Success !");
                    clearAndSetPrompt(passwordVerifier, "Success !");
                    timer(() -> initGetFile(buttonHide, buttonShow, paneShow, paneHide));
                } catch (Exception e) {
                    clearAndSetPrompt(passwordLogin, "Error !");
                    clearAndSetPrompt(passwordVerifier, "Error !");
                }
            } else {
                clearAndSetPrompt(passwordLogin, "too short (10 char min) !");
                clearAndSetPrompt(passwordVerifier, "too short  (10 char min) !");
            }
        } else {
            clearAndSetPrompt(passwordLogin, "No match !");
            clearAndSetPrompt(passwordVerifier, "No match !");
        }
    }
    public void getAndCopyFile(Label labelStep1, Button buttonShow, Button buttonHide, AnchorPane paneHide, AnchorPane paneShow){
        //Va chercher un fichier
       // try {
            //pathManager.inscriptionGetAndCopyFile();
            labelStep1.setStyle("-fx-text-fill: #40AB60;");
            buttonHide.setDisable(true);
            timer(() -> initSetFile(buttonShow, buttonHide, paneHide, paneShow));
       // } catch (IOException e){
            System.err.println("Impossible de copier le fichier source");
       // }
    }
    public void setFile(Label labelStep2) {
        Chiffrement chiffrement = new Chiffrement();
        try {
            chiffrement.chiffrerPassword(password);
        } catch (NoSuchAlgorithmException e){
            System.err.println("Imposssible de chiffrer le password");
        }

        //pathManager.inscriptionSetFile();

        //Réussite
        labelStep2.setStyle("-fx-text-fill: #40AB60;");
        timer(this::close);
    }
    public void setPassAndPref(){
        //pathManager.inscriptionSetPassAndPref();
    }
    private void initGetFile(Button buttonHide, Button buttonShow, AnchorPane paneShow, AnchorPane paneHide){
        buttonHide.setVisible(false);
        buttonShow.setDisable(false);
        buttonShow.setVisible(true);
        paneShow.setVisible(true);
        paneHide.setDisable(true);
        paneHide.setVisible(false);
    }
    private void initSetFile(Button buttonShow, Button buttonHide, AnchorPane paneHide, AnchorPane paneShow){
        buttonHide.setVisible(false);
        buttonHide.setDisable(true);
        buttonShow.setVisible(true);
        buttonShow.setDisable(false);
        paneHide.setVisible(false);
        paneShow.setVisible(true);
    }
    private void timer(Runnable action) { //UX
        Timeline time = new Timeline(new KeyFrame(
                Duration.millis(1000),
                actionEvent -> {
                    action.run();
                })
        );
        time.play();
    }
    private void clearAndSetPrompt(PasswordField password, String prompt){ //Design
        password.clear();
        password.setPromptText(prompt);
    }
    private void close() {
        InscriptionStage.inscriptionClose();
    }
}