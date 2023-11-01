package com.memorize.memorize.logic;

import com.memorize.memorize.logic.security.PathManager;
import com.memorize.memorize.stages.InscriptionStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class InscriptionStep {
    private final int LENGTH = 1;
    private final PathManager pathManager = new PathManager();
    public void verification(PasswordField passwordLogin, PasswordField passwordVerifier, Button buttonHide,
                             Button buttonShow, AnchorPane paneShow, AnchorPane paneHide) {
        if (passwordLogin.getText().equals(passwordVerifier.getText())){
            if (passwordVerifier.getLength()>= LENGTH) {
                try {
                    if (pathManager.encryptPass(String.valueOf(passwordVerifier.getText()))){
                        buttonHide.setDisable(true);
                        clearAndSetPrompt(passwordLogin, "Success !");
                        clearAndSetPrompt(passwordVerifier, "Success !");
                        timer(() -> initGetFile(buttonHide, buttonShow, paneShow, paneHide));
                    }
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
        if (pathManager.inscriptionGetCopyAdd()){
            labelStep1.setStyle("-fx-text-fill: #40AB60;");
            buttonHide.setDisable(true);
            timer(() -> initSetFile(buttonShow, buttonHide, paneHide, paneShow));
        }
    }
    public void setFiles(Label labelStep2, Button buttonSetFile) {
        if (pathManager.inscriptionSetFiles()){
            buttonSetFile.setDisable(true);
            labelStep2.setStyle("-fx-text-fill: #40AB60;");
            timer(this::close);
        }
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