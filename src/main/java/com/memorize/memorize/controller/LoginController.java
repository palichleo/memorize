package com.memorize.memorize.controller;

import com.memorize.memorize.logic.AuthLogic;

import com.memorize.memorize.stages.InscriptionStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;

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
    private Pane draggablePane;
    @FXML
    private PasswordField passwordLogin;
    /*
    Classes
     */
    private final AuthLogic authLogic = new AuthLogic();
    private final ShiftUI shiftUI = new ShiftUI();
    /*
    Initialization
     */
    @FXML
    protected void initialize() {
        shiftUI.paneDragSettings(draggablePane);

        draggablePane.getStyleClass().add("draggablePane");
        passwordLogin.getStyleClass().add("passwordLogin");
        buttonConnect.getStyleClass().add("buttonConnect");
        buttonInscription.getStylesheets().add("buttonInscription");
        buttonClose.getStyleClass().add("buttonClose");
    }
    /*
    Control
     */
    @FXML
    protected void setConnectBtn(){
        authLogic.inscritVerifier(passwordLogin, buttonConnect);
    }
    @FXML

    protected void setInscriptionBtn(){
        passwordLogin.clear();
        passwordLogin.setPromptText("password");
        InscriptionStage in = new InscriptionStage();
        in.inscriptionStage();
    }
    @FXML
    protected void closeBtn() {
        System.exit(0);
    }
}