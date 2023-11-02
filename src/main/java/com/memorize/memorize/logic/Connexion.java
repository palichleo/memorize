package com.memorize.memorize.logic;

import com.memorize.memorize.logic.security.Encryption;
import com.memorize.memorize.logic.security.PathManager;
import com.memorize.memorize.stages.LoginStage;
import com.memorize.memorize.stages.MainStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.util.Duration;

public class Connexion {
    /*
    *   Verifier si le mot de passe est bon, si oui, ouvrir le main
    */
    PathManager pathManager = new PathManager();
    public void access(PasswordField passwordField, Button connect){
            if (valid(passwordField.getText())) {
                MainStage main = new MainStage();
                main.memorizeMain();
                LoginStage.connexionClose();
            } else {
                passwordField.clear();
                passwordField.setPromptText("wrong password");
                counterBruteForce(connect);
            }
    }
    private void counterBruteForce(Button connect) { //Security Brute Force protection
        connect.setDisable(true);
        Timeline time = new Timeline(new KeyFrame(
                Duration.millis(450),
                actionEvent -> {
                    enableButton(connect);
                })
        );
        time.play();
    }
    private void enableButton(Button connect){
        connect.setDisable(false); //enable button
    }
    private boolean valid(String password) {
        try {
            return password.equals(pathManager.decrypt()); //Retourne true si le mot de passe est bon
        } catch (Exception e){
            return false;
        }
    }
}
