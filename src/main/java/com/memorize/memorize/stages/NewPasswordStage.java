package com.memorize.memorize.stages;

import com.memorize.memorize.Memorize;
import com.memorize.memorize.controller.MainController;
import com.memorize.memorize.controller.NewPasswordController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class NewPasswordStage {
    private static Stage pass;
    private final FXMLLoader inFXML = new FXMLLoader(Memorize.class.getResource("newPassword-view.fxml"));
    public MainController mainController;
    public NewPasswordStage(MainController controller){
        this.mainController = controller;
    }
    public void newPasswordStage() {
        try {
            pass = new Stage();
            pass.setTitle("New Password");
            pass.initStyle(StageStyle.UNDECORATED);
            pass.setResizable(false);
            pass.initModality(Modality.APPLICATION_MODAL); //Les autres stages sont inutilisables

            Scene passScene = new Scene(inFXML.load(), 330, 200);
            passScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/memorize/memorize/style/main.css")).toExternalForm());

            NewPasswordController newPasswordController = inFXML.getController();
            newPasswordController.setMain(mainController);

            pass.setScene(passScene);
            pass.show();
        } catch (IOException e){
            System.err.println("Une erreur s'est produite (new pass)" + e.getMessage());
        }
    }
    public static void passClose() {
        pass.close();
    }
}
