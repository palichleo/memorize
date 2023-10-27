package com.memorize.memorize.controller;

import com.memorize.memorize.logic.GridLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainController {
    /*
    Nodes
     */
    @FXML
    private Button buttonClose;
    @FXML
    private Button buttonShift;
    @FXML
    private Button buttonAdd;
    @FXML
    private Pane draggablePane;
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
        buttonClose.getStyleClass().add("close-button");
        buttonShift.getStyleClass().add("buttonShift");
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
    protected void handleButtonAction() {
        shiftUI.nodeShift(gridPane, buttonShift, 1600, 1000, "left");
    }
    @FXML
    protected void setButtonAdd(){
        gridLogic.setVboxesChild(0, 0, 'G');


    }
}
