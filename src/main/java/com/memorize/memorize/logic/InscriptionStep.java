package com.memorize.memorize.logic;

import com.memorize.memorize.stages.InscriptionStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.PasswordField;
import javafx.util.Duration;

public class InscriptionStep {
    public void verification(PasswordField password, PasswordField passwordVerifier) {
        if (password.getText().equals(passwordVerifier.getText())){
            if (lenght(passwordVerifier)) {
                //Appel de la méthode permettant l'inscription
                try {
                    Chiffrement chiffrement = new Chiffrement();
                    chiffrement.chiffrerPassword(String.valueOf(passwordVerifier.getText()));
                    //design
                    clearAndSetPrompt(password, "Success !");
                    clearAndSetPrompt(passwordVerifier, "Success !");
                    timerClose();//Timer de 2 secondes avant fermeture
                } catch (Exception e) {
                    //design
                    clearAndSetPrompt(password, "Error !");
                    clearAndSetPrompt(passwordVerifier, "Error !");
                }
            } else {
                clearAndSetPrompt(password, "too short (10 char min) !");
                clearAndSetPrompt(passwordVerifier, "too short  (10 char min) !");
            }
        } else {
            //design
            clearAndSetPrompt(password, "No match !");
            clearAndSetPrompt(passwordVerifier, "No match !");
        }
    }
    private boolean lenght(PasswordField passwordVerifier){
            return passwordVerifier.getLength()>9;
    }
    private void clearAndSetPrompt(PasswordField password, String prompt){ //Design
        password.clear();
        password.setPromptText(prompt);
    }
    private void timerClose() { //UX
        Timeline time = new Timeline(new KeyFrame(
                Duration.millis(1250),
                actionEvent -> {
                        insClose();
                })
        );
        time.play();
    }
    private void insClose() {
        InscriptionStage.inscriptionClose();
    }
}