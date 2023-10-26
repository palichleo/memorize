package com.memorize.memorize.controller;

import com.memorize.memorize.logic.InscriptionLogic;
import com.memorize.memorize.stages.InscriptionStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;

public class InscriptionController {
    /*
    Nodes
     */
    @FXML
    private Button buttonClose;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordVerifier;
    @FXML
    private Pane draggablePane;
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
        buttonClose.getStyleClass().add("close-button");
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
        inscriptionLogic.verification(password, passwordVerifier);
    }
}
