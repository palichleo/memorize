package com.memorize.memorize.stages;

import com.memorize.memorize.Memorize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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
            login.setTitle("Login");
            login.setResizable(false);
            login.initStyle(StageStyle.TRANSPARENT);

            Scene sceneConnect = new Scene(fxmlLoader.load(), 230, 164);
            sceneConnect.setFill(Color.TRANSPARENT);
            sceneConnect.getStylesheets().add(Objects.requireNonNull(LoginStage.class.getResource("/com/memorize/memorize/style/login.css")).toExternalForm());

            login.setScene(sceneConnect);
        } catch (IOException e){
            System.err.println(e.getMessage());
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

