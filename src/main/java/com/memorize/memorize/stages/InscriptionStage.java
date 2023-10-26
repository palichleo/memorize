package com.memorize.memorize.stages;

import com.memorize.memorize.Memorize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class InscriptionStage {
    private static Stage in;
    private final FXMLLoader inFXML = new FXMLLoader(Memorize.class.getResource("inscription-view.fxml"));
    public Stage inscriptionStage() {
        try {
            in = new Stage();
            in.setTitle("Inscription");
            in.initStyle(StageStyle.UNDECORATED);
            in.setResizable(false);
            in.initModality(Modality.APPLICATION_MODAL); //Les autres stages sont inutilisables

            Scene inScene = new Scene(inFXML.load(), 227, 155);
            inScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/memorize/memorize/style/login.css")).toExternalForm());

            in.setScene(inScene);
            in.show();
            return in;
        } catch (IOException e){
            System.err.println("Une erreur s'est produite"+e.getMessage());
            return in;
        }
    }
    public static void inscriptionClose() {
        in.close();
    }
}
