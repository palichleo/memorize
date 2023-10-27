package com.memorize.memorize.controller;

import com.memorize.memorize.logic.GridLogic;
import com.memorize.memorize.stages.NewPasswordStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class NewPasswordController {
    /*
    Nodes
     */
    @FXML
    protected Button buttonNewPass;
    @FXML
    private Button buttonClose;
    @FXML
    protected AnchorPane draggablePane;
    @FXML
    private PasswordField passwordLogin;
    @FXML
    private PasswordField passwordVerifier;
    @FXML
    private AnchorPane anchorBackground;
    @FXML
    private VBox vboxVisualyze;
    /*
   Classes
    */
    ShiftUI shiftUI = new ShiftUI();
    GridLogic gridLogic = new GridLogic();
    /*
    Initialization
     */
    @FXML
    protected void initialize(){
        shiftUI.paneDragSettings(draggablePane);

        vboxVisualyze.getStyleClass().add("cell");
        buttonClose.getStyleClass().add("buttonClose");
        buttonNewPass.getStyleClass().add("buttonNewPass");
        anchorBackground.getStyleClass().add("anchorBackground");
        draggablePane.getStyleClass().add("draggablePane");
        passwordLogin.getStyleClass().add("passwordLogin");
        passwordVerifier.getStyleClass().add("passwordVerifier");
    }
    /*
    Control
     */
    @FXML
    protected void setPasswordButton(){
        gridLogic.setVboxesChild(0, 0, 'G');

    }
    @FXML
    protected void closeBtn(){NewPasswordStage.passClose();}

    private void verifierSiMemeSite(){



    }
}
