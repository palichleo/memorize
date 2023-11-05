package com.memorize.memorize.controller;

import com.memorize.memorize.logic.Connexion;

import com.memorize.memorize.logic.security.PathManager;
import com.memorize.memorize.stages.InscriptionStage;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

public class LoginController {
    /*
    Nodes
     */
    @FXML
    private Button buttonClose;
    @FXML
    private Button buttonConnect;
    @FXML
    private Button buttonInscription;
    @FXML
    private Button buttonKey;
    @FXML
    private AnchorPane draggablePane;
    @FXML
    private PasswordField passwordLogin;
    @FXML
    private AnchorPane anchorBackground;
    @FXML
    private AnchorPane anchorPaneBackConnect;
    @FXML
    private AnchorPane paneBehindTextbox;
    @FXML
    private Label labelLogin;
    /*
    Classes
     */
    private final Connexion authLogic = new Connexion();
    private final DesignUI designUI = new DesignUI();
    private final PathManager pathManager = new PathManager();
    /*
    Initialization
     */
    @FXML
    protected void initialize() {
        String username = System.getProperty("user.name");

        designUI.paneDragSettings(draggablePane);

        buttonConnect.getStyleClass().add("buttonConnect");
        buttonInscription.getStyleClass().add("buttonConnect");
        buttonClose.getStyleClass().add("buttonClose");
        buttonKey.getStyleClass().add("buttonKey");
        anchorBackground.getStyleClass().add("anchorBackground");
        anchorPaneBackConnect.getStyleClass().add("anchorPaneBackConnect");
        paneBehindTextbox.getStyleClass().add("paneBehindTextbox");
        draggablePane.getStyleClass().add("draggablePane");
        passwordLogin.getStyleClass().add("passwordLogin");

        labelLogin.setText("Login ~ " + username);

        if (pathManager.inscritVerif()){
            passwordLogin.setPromptText("password");
            buttonConnect.setVisible(true);
            buttonInscription.setVisible(false);
            buttonConnect.setDisable(false);
            buttonInscription.setDisable(true);
        } else {
            buttonConnect.setVisible(false);
            buttonInscription.setVisible(true);
            buttonConnect.setDisable(true);
            buttonInscription.setDisable(false);
            passwordLogin.clear();
            passwordLogin.setPromptText("Install needed !");
            passwordLogin.setDisable(true);
        }

        Timeline timeline = designUI.getTimeline(passwordLogin);
        timeline.play();
    }
    /*
    Control
     */
    @FXML
    protected void setConnectBtn(){
        authLogic.access(passwordLogin, buttonConnect);
    }
    @FXML
    protected void setButtonKey(){
        pathManager.reassignmentKeyPath();
    }
    @FXML
    protected void setInscriptionBtn(){
        InscriptionStage in = new InscriptionStage();
        in.inscriptionStage();

        Platform.runLater(() -> {
            buttonConnect.setVisible(true);
            buttonInscription.setVisible(false);
            buttonConnect.setDisable(false);
            buttonInscription.setDisable(true);
            passwordLogin.clear();
            passwordLogin.setPromptText("password");
            passwordLogin.setDisable(false);
        });
    }
    @FXML
    protected void closeBtn() {
        System.exit(0);
    }
}