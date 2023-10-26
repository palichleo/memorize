package com.memorize.memorize.stages;

import com.memorize.memorize.Memorize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class LoginStage {
    private static Stage login;
    private static final FXMLLoader fxmlLoader = new FXMLLoader(Memorize.class.getResource("login-view.fxml"));

    private static void connexion() {
        try {
            login = new Stage();
            login.initStyle(StageStyle.UNDECORATED);
            login.setTitle("Login");
            login.setResizable(false);

            Scene sceneConnect = new Scene(fxmlLoader.load(), 227, 155);
            sceneConnect.getStylesheets().add(Objects.requireNonNull(LoginStage.class.getResource("/com/memorize/memorize/style/login.css")).toExternalForm());

            login.setScene(sceneConnect);
        } catch (IOException e){
            System.err.println("Une erreur s'est produite"+e.getMessage());
        }
    }
    public static void connexionShow(){
        connexion();
        login.show();
    }
    public static void connexionClose(){
        login.close();
    }
}

