package com.memorize.memorize.controller;

import com.memorize.memorize.logic.InscriptionLogic;
import com.memorize.memorize.stages.InscriptionStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
    /*
    Classes
     */
    private final ShiftUI shiftUI = new ShiftUI();
    private final InscriptionLogic inscriptionLogic = new InscriptionLogic();
    /*
    Initialization
     */
    @FXML
    protected void initialize(){
        shiftUI.paneDragSettings(draggablePane);

        buttonClose.getStyleClass().add("buttonClose");
        buttonInscription.getStyleClass().add("buttonInscription");
        anchorBackground.getStyleClass().add("anchorBackground");
        draggablePane.getStyleClass().add("draggablePane");
        passwordLogin.getStyleClass().add("passwordLogin");
        passwordVerifier.getStyleClass().add("passwordVerifier");
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
