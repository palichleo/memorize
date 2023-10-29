package com.memorize.memorize.controller;

import com.memorize.memorize.logic.InscriptionLogic;
import com.memorize.memorize.stages.InscriptionStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class InscriptionController {
    /*
    Nodes
     */
    @FXML
    private Button buttonClose;
    @FXML
    private Button buttonInscription;
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
    private Label labelInstall;

    /*
    Classes
     */
    private final DesignUI designUI = new DesignUI();
    private final InscriptionLogic inscriptionLogic = new InscriptionLogic();
    /*
    Initialization
     */
    @FXML
    protected void initialize(){
        designUI.paneDragSettings(draggablePane);

        buttonClose.getStyleClass().add("buttonClose");
        buttonInscription.getStyleClass().add("buttonConnect");
        anchorBackground.getStyleClass().add("anchorBackground");
        anchorPaneBackInscription.getStyleClass().add("anchorPaneBackConnect");
        paneBehindTextbox.getStyleClass().add("paneBehindTextbox");
        draggablePane.getStyleClass().add("draggablePane");
        passwordLogin.getStyleClass().add("passwordLogin");
        passwordVerifier.getStyleClass().add("passwordLogin");

        String username = System.getProperty("user.name");
        labelInstall.setText("Install ~ " + username);

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
    protected void setInscriptionBtn() {
        inscriptionLogic.verification(passwordLogin, passwordVerifier);
    }
}
