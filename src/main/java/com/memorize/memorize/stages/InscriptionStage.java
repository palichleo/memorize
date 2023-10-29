package com.memorize.memorize.stages;

import com.memorize.memorize.Memorize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class InscriptionStage {
    private static Stage in;
    private final FXMLLoader inFXML = new FXMLLoader(Memorize.class.getResource("inscription-view.fxml"));
    public void inscriptionStage() {
        try {
            in = new Stage();
            in.setTitle("Inscription");
            in.initStyle(StageStyle.TRANSPARENT);
            in.setResizable(false);
            in.initModality(Modality.APPLICATION_MODAL); //Les autres stages sont inutilisables

            Scene inScene = new Scene(inFXML.load(), 230, 206);
            inScene.setFill(Color.TRANSPARENT);
            inScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/memorize/memorize/style/login.css")).toExternalForm());

            in.setScene(inScene);
            in.show();
        } catch (IOException e){
            System.err.println("Une erreur s'est produite dans l'inscription"+e.getMessage());
        }
    }
    public static void inscriptionClose() {
        in.close();
    }
}
