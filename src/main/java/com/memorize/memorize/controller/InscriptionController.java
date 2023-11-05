package com.memorize.memorize.controller;

import com.memorize.memorize.logic.InscriptionStep;
import com.memorize.memorize.stages.InscriptionStage;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class InscriptionController {
    /*
    Nodes
     */
    @FXML
    private Button buttonClose;
    @FXML
    private Button buttonGetPSWD;
    @FXML
    private Button buttonGetFile;
    @FXML
    private Button buttonSetFile;
    @FXML
    private PasswordField passwordLogin;
    @FXML
    private PasswordField passwordVerifier;
    @FXML
    private AnchorPane draggablePane;
    @FXML
    private AnchorPane anchorBackground;
    @FXML
    private AnchorPane anchorPaneBackInscription;
    @FXML
    private AnchorPane paneBehindTextbox;
    @FXML
    private AnchorPane paneGiveInstruction2;
    @FXML
    private AnchorPane paneGiveInstruction1;
    @FXML
    private Label labelInstall;
    @FXML
    private VBox vboxStep1;
    @FXML
    private VBox vboxStep2;
    @FXML
    private Label labelStep1;
    @FXML
    private Label labelStep2;

    /*
    Classes
     */
    private final DesignUI designUI = new DesignUI();
    private final InscriptionStep inscriptionStep = new InscriptionStep();
    /*
    Initialization
     */
    @FXML
    protected void initialize(){
        String username = System.getProperty("user.name");

        designUI.paneDragSettings(draggablePane);

        buttonClose.getStyleClass().add("buttonClose");
        buttonGetPSWD.getStyleClass().add("buttonConnect");
        buttonGetFile.getStyleClass().add("buttonConnect");
        buttonSetFile.getStyleClass().add("buttonConnect");
        anchorBackground.getStyleClass().add("anchorBackground");
        anchorPaneBackInscription.getStyleClass().add("anchorPaneBackConnect");
        paneBehindTextbox.getStyleClass().add("paneBehindTextbox");
        draggablePane.getStyleClass().add("draggablePane");
        passwordLogin.getStyleClass().add("passwordLogin");
        passwordVerifier.getStyleClass().add("passwordLogin");
        paneGiveInstruction1.getStyleClass().add("paneBehindTextbox");
        paneGiveInstruction2.getStyleClass().add("paneBehindTextbox");
        vboxStep1.getStyleClass().add("vboxStep");
        vboxStep2.getStyleClass().add("vboxStep");
        labelStep1.setStyle("-fx-text-fill: #DB2323;");
        labelStep2.setStyle("-fx-text-fill: #DB2323;");

        labelInstall.setText("Install ~ " + username);

        buttonGetFile.setVisible(false);
        buttonSetFile.setVisible(false);
        buttonGetFile.setDisable(true);
        buttonSetFile.setDisable(true);
        paneGiveInstruction1.setVisible(false);
        paneGiveInstruction2.setVisible(false);

        Timeline timeline = designUI.getTimeline(passwordLogin, passwordVerifier);
        timeline.play();
    }
    /*
    Control
     */
    @FXML
    protected void closeBtn(){
        InscriptionStage.inscriptionClose();
    }

    @FXML
    protected void setButtonGetPSWD() {
        inscriptionStep.verification(passwordLogin, passwordVerifier, buttonGetPSWD, buttonGetFile, paneGiveInstruction1, paneBehindTextbox);
    }
    @FXML
    protected void setButtonGetFile() {
       inscriptionStep.getAndCopyFile(labelStep1,buttonSetFile, buttonGetFile, paneGiveInstruction1, paneGiveInstruction2);
    }
    @FXML
    protected void setButtonSetFile() {
        inscriptionStep.setFiles(labelStep2, buttonSetFile);
    }
}
