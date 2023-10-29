package com.memorize.memorize.controller;

import com.memorize.memorize.logic.GridLogic;
import com.memorize.memorize.stages.NewPasswordStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class MainController {
    /*
    Nodes
     */
    @FXML
    private Button buttonClose;
    @FXML
    private Button buttonShiftRight;
    @FXML
    private Button buttonShiftLeft;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonReset;
    @FXML
    private AnchorPane draggablePane;
    @FXML
    private AnchorPane anchorBackground;
    @FXML
    private GridPane gridPane;
    /*
    Classes & var
     */
    private final ShiftUI shiftUI = new ShiftUI();
    private final GridLogic gridLogic = new GridLogic();
    private int pos = 0;

    /*
    Initialization
     */
    @FXML
    protected void initialize() {
        shiftUI.paneDragSettings(draggablePane);
        buttonClose.getStyleClass().add("buttonClose");
        buttonShiftRight.getStyleClass().add("buttonShift");
        buttonShiftLeft.getStyleClass().add("buttonShift");
        buttonReset.getStyleClass().add("buttonReset");
        draggablePane.getStyleClass().add("draggablePane");
        anchorBackground.getStyleClass().add("anchorBackground");
        gridPane.getStyleClass().add("gridPane");
        buttonAdd.getStyleClass().add("buttonAdd");
        gridLogic.resetAndCreate(gridPane);
    }
    /*
    Control
     */
    @FXML
    protected void buttonClose() {
        System.exit(0);
    }
    private boolean mur(int LR) { //Méthode pour empecher l'utilisateur d'aller vers la gauche avec le gridpane 'méthode mur'
        if (LR==0) {
            return pos > 0;
        } else {
            return pos==0; //on pourra remplacer par ==1 et ==2 et .. quand il y aura plusieurs pages..
        }
    }
    @FXML
    protected void setButtonShiftRight() {
        if (mur(1)) {
            shiftUI.nodeShift(gridPane, buttonShiftRight, 1600, 600, "left");
            pos++;
        }
    }
    @FXML
    protected void setButtonShiftLeft() {
        if (mur(0)) {
            shiftUI.nodeShift(gridPane, buttonShiftRight, 1600, 600, "right");
            pos--;
        }
    }
    @FXML
    protected void setButtonAdd() {
        NewPasswordStage newPasswordStage = new NewPasswordStage(this);
        newPasswordStage.newPasswordStage();
    }
    protected void refresh() {


    }
}
