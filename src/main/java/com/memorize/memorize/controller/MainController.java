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
    private AnchorPane draggablePane;
    @FXML
    private GridPane gridPane;
    /*
    Classes
     */
    private final ShiftUI shiftUI = new ShiftUI();
    private final GridLogic gridLogic = new GridLogic();
    /*
    Initialization
     */
    @FXML
    protected void initialize() {
        shiftUI.paneDragSettings(draggablePane);
        buttonClose.getStyleClass().add("buttonClose");
        buttonShiftRight.getStyleClass().add("buttonShift");
        buttonShiftLeft.getStyleClass().add("buttonShift");
        draggablePane.getStyleClass().add("draggablePane");
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
    @FXML
    protected void setButtonShiftRight() {
        shiftUI.nodeShift(gridPane, buttonShiftRight, 1600, 1000, "left");
    }
    @FXML
    protected void setButtonShiftLeft() {
        shiftUI.nodeShift(gridPane, buttonShiftRight, 1600, 1000, "right");
    }
    @FXML
    protected void setButtonAdd(){
        NewPasswordStage newPasswordStage = new NewPasswordStage(this);
        newPasswordStage.newPasswordStage();
    }
    protected void refresh(){


    }
}
