package com.memorize.memorize;

import javafx.application.Application;

import javafx.stage.Stage;
import com.memorize.memorize.stages.LoginStage;

public class Memorize extends Application {

    @Override
    public void start(Stage connexion) {LoginStage.connexionShow();
    }

    public static void main(String[] args) {
        launch();
    }
}

