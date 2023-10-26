package com.memorize.memorize.logic;

import com.memorize.memorize.stages.InscriptionStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.PasswordField;
import javafx.util.Duration;

public class InscriptionLogic {
    public void verification(PasswordField password, PasswordField passwordVerifier) {
        if (password.getText().equals(passwordVerifier.getText())){
            if (lengh(passwordVerifier)) {
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
                clearAndSetPrompt(passwordVerifier, "Trop court (10 minimum)");
            }
        } else {
            //design
            clearAndSetPrompt(password, "No match !");
            clearAndSetPrompt(passwordVerifier, "No match !");
        }
    }
    private boolean lengh(PasswordField passwordVerifier){
            return passwordVerifier.getLength()>10;
    }
    private void clearAndSetPrompt(PasswordField password, String prompt){ //Design
        password.clear();
        password.setPromptText(prompt);
    }
    private void timerClose() { //UX
        Timeline time = new Timeline(new KeyFrame(
                Duration.millis(1300),
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

