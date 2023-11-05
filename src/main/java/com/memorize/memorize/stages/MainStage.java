package com.memorize.memorize.stages;

import com.memorize.memorize.Memorize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class MainStage {
    private final FXMLLoader memorizeMainFXML = new FXMLLoader(Memorize.class.getResource("memorize-view.fxml"));
    private final Stage memorize = new Stage();
    public void memorizeMain() {
        try {
            memorize.setTitle("Memorize");
            memorize.setResizable(false);
            memorize.initStyle(StageStyle.TRANSPARENT);

            Scene memorizeScene = new Scene(memorizeMainFXML.load(), 820, 620);
            memorizeScene.setFill(Color.TRANSPARENT);
            memorizeScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/memorize/memorize/style/main.css")).toExternalForm());

            memorize.setScene(memorizeScene);
            memorize.show();
        } catch (IOException e){
            System.err.println("Une erreur s'est produite dans l'initialisation du main"+e.getMessage());
        }
    }
}
