package com.memorize.memorize.logic;

import com.memorize.memorize.stages.LoginStage;
import com.memorize.memorize.stages.MainClass;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.util.Duration;

public class AuthLogic {
    private final Chiffrement chiffrement = new Chiffrement();
    private final PathManager pathManager = new PathManager();
    /*
    *   Verifier si l'utilisateur est inscrit, si non, ouvrir une page d'inscription
    */
    public void inscritVerifier(PasswordField password, Button connect){
        if (pathManager.inscritVerif()){
            access(password, connect);
        } else {
            password.clear();
            password.setPromptText("Inscrivez-vous !");
        }
    }
    /*
    *   Verifier si le mot de passe est bon, si oui, ouvrir le main
    */
    private void access(PasswordField passwordField, Button connect){
            if (valid(passwordField.getText())) {
                MainClass main = new MainClass();
                main.memorizeMain();
                LoginStage.connexionClose();
            } else {
                passwordField.clear();
                counterBruteForce(connect);
            }
    }
    private void counterBruteForce(Button connect) { //Security
        connect.setDisable(true);
        Timeline time = new Timeline(new KeyFrame(
                Duration.millis(2000),
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
        return true; //password.equals(chiffrement.dechiffrerPassword()); //Retourne true si le mot de passe est bon
    }
}
